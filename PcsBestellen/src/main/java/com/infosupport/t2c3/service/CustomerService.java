package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.exceptions.ItemNotFoundException;
import com.infosupport.t2c3.repositories.CustomerRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Leon Stam on 18-1-2016.
 */
@RestController
@RequestMapping(value = "/employee/customers", produces = "application/json")
public class CustomerService extends AbsRestService<Customer> {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping(value = "/{id}/creditLimit", method = RequestMethod.PUT)
    public ResponseEntity editCreditLimit(@PathVariable("id") long customerId,
                                          @RequestParam("creditLimit") BigDecimal newLimit) throws ItemNotFoundException {
        Customer customer = getById(customerId);
        customer.setCreditLimit(newLimit);
        customerRepository.save(customer);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Override
    public BasicRepository<Customer> provideRepo() {
        return customerRepository;
    }

}


