package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.esb.DataVaultService;
import com.infosupport.t2c3.repositories.OrderRepository;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 13-1-2016.
 */
@RestController
@RequestMapping(value = "/employeeorder", produces = "application/json")
@CrossOrigin
@Setter
public class OrderEditService {

    private static final String EMPLOYEE_MODIFY_ORDER = "EMPLOYEE_MODIFY_ORDER";

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private DataVaultService dataVaultService;

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
     *
     * @param id the order id
     * @return the order
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Order getOrderById(@PathVariable Long id) {
        return orderRepo.findOne(id);
    }

    //TODO: Convert this to ÌD based put request
    /**
     * Edit an order.
     *
     * @param order the order to be edited
     * @return response 200 OK
     */
    @RequestMapping(method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<String> editOrder(@RequestBody Order order) {
        orderRepo.save(order);
        dataVaultService.store(EMPLOYEE_MODIFY_ORDER, order);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
