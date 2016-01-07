package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.repositories.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */

@RestController
@RequestMapping(value = "/order", produces = "application/json")
@CrossOrigin
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    /**
     *Get all the orders from the repo.
     * @return All the orders
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Send order to the backend. Repo passes it to the dababase.
     *
     * @param order The order to be persisted.
     * @return the order
     */
    @RequestMapping(method = RequestMethod.POST, consumes = "application/json")
    public Order placeOrder(@RequestBody Order order) {
        orderRepository.save(order);

        //TODO send succes message
        return order;
    }
}
