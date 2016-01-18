package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.accounts.Credentials;
import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.repositories.CredentialsRepository;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.security.SecurityService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 6-1-2016.
 */
@RestController
@RequestMapping(value = "/customers", produces = "application/json")
public class CustomerService extends AbsRestService<Customer> {

    //TODO remove
    @Override
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Customer getById(@PathVariable("id") final long id) {
        return super.getById(id);
    }

    //TODO remove
    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> getAll() {
        return super.getAll();
    }


    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CredentialsRepository credentialsRepo;

    @Autowired
    private SecurityService securityService;

    //TODO remove
    @RequestMapping(value = "/credentials", method = RequestMethod.GET)
    public List<Credentials> getAllCredentials() {
        return credentialsRepo.findAll();
    }

    @Override
    public BasicRepository<Customer> provideRepo() {
        return customerRepo;
    }

    /**
     * Get all the orders from a customer checking the token.
     *
     * @param tokenValue the tokenvalue
     * @param id         the customerId
     * @return the orders
     */
    @RequestMapping(method = RequestMethod.GET, value = "/{id}/orders")
    public ResponseEntity<List<Order>> getAllOrdersForCustomer(
            @RequestHeader(value = "tokenValue") String tokenValue,
            @PathVariable Long id) {
        Customer customer = customerRepo.findOne(id);
        if (securityService.checkTokenForCustomer(id, tokenValue)) {
            return new ResponseEntity(customer.getOrders(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }

}
