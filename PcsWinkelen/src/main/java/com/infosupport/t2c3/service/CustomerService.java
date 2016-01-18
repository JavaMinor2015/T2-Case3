package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.accounts.Credentials;
import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.repositories.CredentialsRepository;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.repositories.OrderRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerService extends AbsRestService<Customer> {

    //TODO remove with init method
    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CredentialsRepository credentialsRepo;

    //TODO remove
    @RequestMapping(value = "/credentials", method = RequestMethod.GET)
    public List<Credentials> getAllCredentials() {
        return credentialsRepo.findAll();
    }

    @Override
    public BasicRepository<Customer> provideRepo() {
        return customerRepo;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}/orders")
    public List<Order> getAllOrdersForCustomer(@RequestHeader(value = "tokenValue") String tokenValue, @PathVariable Long id){
        Customer customer = customerRepo.findOne(id);
        System.out.println(tokenValue);
        return customer.getOrders();
    }

}
