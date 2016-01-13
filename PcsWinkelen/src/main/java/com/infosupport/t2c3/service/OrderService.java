package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.customers.Customer;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.domain.orders.OrderItem;
import com.infosupport.t2c3.domain.products.Product;
import com.infosupport.t2c3.model.OrderRequest;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.repositories.OrderRepository;
import com.infosupport.t2c3.repositories.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@CrossOrigin
@Setter
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;
    @Autowired
    private ProductRepository productRepo;
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
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {

        Order newOrder = calculatePrices(orderRequest.getOrder());
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
    private Order calculatePrices(Order order) {
        order.setTotalPrice(new BigDecimal(0.0));

        for (OrderItem item : order.getItems()) {
            Product product = productRepo.findOne(item.getProduct().getId());
            item.setPrice(product.getPrice());
            BigDecimal pricePerItem = item.getPrice().multiply(new BigDecimal(item.getAmount()));
            BigDecimal totalPrice = order.getTotalPrice().add(pricePerItem);
            order.setTotalPrice(totalPrice);
        }
        return order;
    }
}
