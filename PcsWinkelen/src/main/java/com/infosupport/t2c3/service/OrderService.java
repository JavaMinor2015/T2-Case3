package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.domain.orders.OrderItem;
import com.infosupport.t2c3.domain.orders.OrderStatus;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.esb.DataVaultService;
import com.infosupport.t2c3.exceptions.CaseException;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.exceptions.MethodNotAllowedException;
import com.infosupport.t2c3.exceptions.OrderAlreadyShippedException;
import com.infosupport.t2c3.model.OrderRequest;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import com.infosupport.t2c3.repositories.SupplyHandler;
import com.infosupport.t2c3.service.abs.AbsSecuredRestService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/order", produces = "application/json")
@Setter
public class OrderService extends AbsSecuredRestService<Order> {

    public static final BigDecimal DEFAULT_CREDIT_LIMIT = BigDecimal.valueOf(100);
    public static final String CUSTOMER_PLACE_ORDER = "CUSTOMER_PLACE_ORDER";


    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private SupplyHandler supplyHandler;
    @Autowired
    private CustomerRepository customerRepo;
    @Autowired
    private DataVaultService dataVaultService;

    @Override
    public BasicRepository<Order> provideRepo() {
        return orderRepo;
    }

    @Override
    public Order getById(@PathVariable("orderId") long id) {
        throw new MethodNotAllowedException();
    }

    @Override
    public List<Order> getAll() {
        throw new MethodNotAllowedException();
    }

    /**
     * Send order to the backend. Repo passes it to the dababase.
     *
     * @param orderRequest The orderRequest to be persisted.
     * @return the order
     * @throws CaseException if an error occurred with placing the order
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    @Transactional(rollbackFor = CaseException.class)
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) throws CaseException {
        //Calculate the order
        Order newOrder = calculatePrices(orderRequest.getOrder());
        newOrder.setStatus(OrderStatus.PLACED);

        //Check for customer & credit limit
        Optional<Customer> customerOptional;
        if (orderRequest.getToken() != null && orderRequest.getToken().getValue() != null) {
            Customer customer = customerRepo.findByCredentialsToken(orderRequest.getToken().getValue());
            checkCreditLimit(newOrder, customer.getCreditLimit());
            customerOptional = Optional.of(customer);
        } else {
            //Default Credit Limit applies
            checkCreditLimit(newOrder, DEFAULT_CREDIT_LIMIT);
            customerOptional = Optional.empty();
        }

        //Decrease Supply
        for (OrderItem orderItem : newOrder.getItems()) {
            supplyHandler.decreaseStock(orderItem.getProduct(), orderItem.getAmount());
        }

        //Save the order
        orderRepo.save(newOrder);

        //Add the customer if present
        if (customerOptional.isPresent()) {
            Customer customer = customerOptional.get();
            customer.addOrder(orderRequest.getOrder());
            customerRepo.save(customer);
        }

        dataVaultService.store(CUSTOMER_PLACE_ORDER, newOrder);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Calculates and sets all prices in the order instance.
     *
     * @param order the order
     * @return the order with prices set
     */
    public Order calculatePrices(Order order) throws ItemNotFoundException {
        order.setTotalPrice(BigDecimal.valueOf(0.0));

        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findOne(item.getProduct().getId());
            if (product == null) {
                throw new ItemNotFoundException("No Product with ID " + item.getProduct().getId());
            }

            //Update product & prices
            item.setProduct(product);
            item.setPrice(product.getPrice());
            BigDecimal pricePerItem = item.getPrice().multiply(BigDecimal.valueOf(item.getAmount()));
            BigDecimal totalPrice = order.getTotalPrice().add(pricePerItem);
            order.setTotalPrice(totalPrice);
        }
        return order;
    }

    /**
     * Edit the address of an order.
     *
     * @param newOrder   order object with the new values.
     * @param tokenValue user must be logged in as owner of the order
     * @param orderId    orderId of the order
     * @return order object with new values
     */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Order> editOrder(
            @RequestBody Order newOrder,
            @RequestHeader String tokenValue,
            @PathVariable Long orderId) {

        Customer customer = customerRepo.findByOrdersId(orderId);
        getCustomer(customer.getId(), tokenValue);

        Order order = super.getById(orderId);
        if (!canBeChanged(order.getStatus())) {
            throw new OrderAlreadyShippedException();
        }

        order.getCustomerData().getAddress().edit(newOrder.getCustomerData().getAddress());
        order.getCustomerData().setFirstName(newOrder.getCustomerData().getFirstName());
        order.getCustomerData().setLastName(newOrder.getCustomerData().getLastName());
        orderRepo.save(order);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    /**
     * Customer can cancel an order.
     *
     * @param orderId    orderId to be cancelled
     * @param tokenValue user must be logged in as the owner of the order
     * @return 200 OK
     */
    @RequestMapping(value = "/{orderId}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId, @RequestHeader String tokenValue) {
        Customer customer = customerRepo.findByOrdersId(orderId);
        getCustomer(customer.getId(), tokenValue);

        Order order = super.getById(orderId);
        if (!canBeChanged(order.getStatus())) {
            throw new OrderAlreadyShippedException();
        }

        //Decrease Supply
        for (OrderItem orderItem : order.getItems()) {
            supplyHandler.increaseStock(orderItem.getProduct(), orderItem.getAmount());
        }
        order.setStatus(OrderStatus.CANCELED);
        orderRepo.save(order);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Check if this order surpasses the credit limit.
     *
     * @param order          The order
     * @param maxCreditLimit The maximum limit
     */
    private void checkCreditLimit(Order order, BigDecimal maxCreditLimit) {
        if (order.getTotalPrice().compareTo(maxCreditLimit) == 1) {
            order.setStatus(OrderStatus.WAIT_FOR_APPROVAL);
        }
    }

    private boolean canBeChanged(OrderStatus orderStatus) {
        switch (orderStatus) {
            case REJECTED:
            case CANCELED:
            case SENT:
                return false;

            default:
                return true;
        }
    }


}
