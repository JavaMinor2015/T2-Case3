package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.domain.orders.*;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.exceptions.CaseException;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.model.OrderRequest;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import com.infosupport.t2c3.repositories.SupplyHandler;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
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
@CrossOrigin
@Setter
public class OrderService {

    //TODO remove with init function
    private static final int MAX_FIFTEEN = 15;
    private static final int MAX_FOUR = 4;
    private static final int MAX_THREE = 3;
    private static final Logger logger = LogManager.getLogger(OrderService.class.getSimpleName());


    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private SupplyHandler supplyHandler;
    @Autowired
    private CustomerRepository customerRepo;


    /**
     * Get all the orders from the repo.
     *
     * @return All the orders
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
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

        //Decrease Supply
        for (OrderItem orderItem : newOrder.getItems()) {
            supplyHandler.decreaseStock(orderItem.getProduct(), orderItem.getAmount());
        }

        //Save the order
        orderRepo.save(newOrder);

        if (orderRequest.getToken() != null && orderRequest.getToken().getValue() != null) {
            Customer customer = customerRepo.findByCredentialsToken(orderRequest.getToken().getValue());
            customer.addOrder(orderRequest.getOrder());
            customerRepo.save(customer);
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * Calculates and sets all prices in the order instance.
     *
     * @param order the order
     * @return the order with prices set
     */
    private Order calculatePrices(Order order) throws ItemNotFoundException {
        order.setTotalPrice(new BigDecimal(0.0));

        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findOne(item.getProduct().getId());
            if (product == null) {
                throw new ItemNotFoundException("No Product with ID " + item.getProduct().getId());
            }

            //Update product & prices
            item.setProduct(product);
            item.setPrice(product.getPrice());
            BigDecimal pricePerItem = item.getPrice().multiply(new BigDecimal(item.getAmount()));
            BigDecimal totalPrice = order.getTotalPrice().add(pricePerItem);
            order.setTotalPrice(totalPrice);
        }
        return order;
    }

    /**
     * Initialize this product service.
     */
    public void init() {
        //TODO: Remove this, is just adding random data
        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 2; i++) {
            List<OrderItem> items = new ArrayList<>();
            for (int a = 0; a < MAX_THREE; a++) {
                items.add(
                        OrderItem.builder()
                        .amount(random.nextInt(MAX_FOUR) + 1)
                        .product(productRepo.findOne((long) random.nextInt(MAX_FIFTEEN) + 1))
                        .build()
                );
            }

            Order order = new Order(
                    null,
                    OrderStatus.PLACED,
                    items,
                    new CustomerData(
                            "Remco",
                            "Groenenboom",
                            "remco@email.com",
                            new Address(
                                    "city",
                                    "street",
                                    "6",
                                    "zipcode")
                    )

            );

            try {
                calculatePrices(order);
            } catch (ItemNotFoundException e) {
                logger.fatal("This should be impossible", e);
            }

            orderRepo.save(order);
        }
    }

}
