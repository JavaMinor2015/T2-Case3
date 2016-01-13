package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.repositories.OrderRepository;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 13-1-2016.
 */
@RestController
@RequestMapping(value = "/editorder", produces = "application/json")
@CrossOrigin
@Setter
public class OrderEditService {

    @Autowired
    private OrderRepository orderRepo;


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
     * Get one order by a id.
     * @param id the order id
     * @return the order
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable Long id) {
        return orderRepo.findOne(id);
    }

    @PostConstruct
    public void init(){
        System.out.println("init aangeroepen");
    }
}
