package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.accounts.Credentials;
import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.domain.orders.Order;
import com.infosupport.t2c3.exceptions.MethodNotAllowedException;
import com.infosupport.t2c3.repositories.CredentialsRepository;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.security.SecurityService;
import com.infosupport.t2c3.service.abs.AbsSecuredRestService;
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
public class CustomerService extends AbsSecuredRestService<Customer> {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private CredentialsRepository credentialsRepo;

    @Autowired
    private SecurityService securityService;

    @Override
    public Customer getById(@PathVariable("id") final long id) {
        throw new MethodNotAllowedException();
    }

    @Override
    public List<Customer> getAll() {
        throw new MethodNotAllowedException();
    }

    @Override
    public BasicRepository<Customer> provideRepo() {
        return customerRepo;
    }

    /**
     * Edit a customer.
     *
     * @param tokenValue  user must be logged in
     * @param id          id of the customer
     * @param newCustomer customer object with the new values
     * @return edited customer with new values
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<Customer> editCustomer(
            @RequestHeader String tokenValue,
            @PathVariable Long id,
            @RequestBody Customer newCustomer) {

        Customer customer = getCustomer(id, tokenValue);
        customer.edit(newCustomer);
        customerRepo.save(customer);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    /**
     * Edit the password of a customer.
     *
     * @param tokenValue     user must be logged in as the customer
     * @param id             id of the customer
     * @param newCredentials credentials object with the new values
     * @return HttpResponse
     */
    @RequestMapping(value = "/{id}/credentials", method = RequestMethod.PUT, consumes = "application/json")
    public ResponseEntity<String> editCredentials(
            @RequestHeader String tokenValue,
            @PathVariable Long id,
            @RequestBody Credentials newCredentials) {

        getCustomer(id, tokenValue);

        Credentials credentials = credentialsRepo.findByToken(tokenValue);
        Credentials credentialsWithHashedPW =
                securityService.createCredentials(newCredentials.getUserName(), newCredentials.getPassword());
        credentials.setPassword(credentialsWithHashedPW.getPassword());
        credentialsRepo.save(credentials);

        return new ResponseEntity<>(HttpStatus.OK);
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
        Customer customer = getCustomer(id, tokenValue);
        return new ResponseEntity<>(customer.getOrders(), HttpStatus.OK);
    }

}
