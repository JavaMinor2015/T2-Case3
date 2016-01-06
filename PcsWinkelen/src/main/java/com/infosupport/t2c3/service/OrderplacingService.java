package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.orders.Order;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */

@RestController
@RequestMapping(value = "/order")
public class OrderplacingService {


    /**
     * Send order to the backend. Repo passes it to the dababase.
     * @param order The order to be persisted.
     * @return  the order
     */
    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json", produces = "application/json")
    public Order placeOrder(@RequestBody Order order) {
        //TODO pass the order to repo

        //TODO send succes message
        return order;
    }
}
