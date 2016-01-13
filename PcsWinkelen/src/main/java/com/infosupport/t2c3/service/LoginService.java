package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.customers.Credentials;
import com.infosupport.t2c3.domain.customers.Customer;
import com.infosupport.t2c3.model.Token;
import com.infosupport.t2c3.repositories.CredentialsRepository;
import com.infosupport.t2c3.repositories.CustomerRepository;
import com.infosupport.t2c3.security.SecurityService;
import java.util.List;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 12-1-2016.
 */
@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin
@Setter
public class LoginService {

    //TODO remove
    @Autowired
    private CredentialsRepository repo;
    //TODO remove
    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private SecurityService securityService;

    //TODO remove
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<Credentials> getAllCredentials() {
        return repo.findAll();
    }

    //TODO remove
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    public List<Customer> getAllCustomers() {
        return customerRepo.findAll();
    }

    /**
     * Log a customer in using his/her credentials.
     *
     * @param credentials credentials of customer
     * @return token
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Token> login(@RequestBody Credentials credentials) {
        Token token;
        String tokenValue = securityService.verify(credentials.getUserName(), credentials.getPassword());
        if (!tokenValue.isEmpty()) {
            Customer customer = customerRepo.findByCredentialsUserName(credentials.getUserName());
            customer.setCredentials(null);
            token = new Token(tokenValue, customer);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

    }

    /**
     * Log a customer out using his/her token.
     *
     * @param token credentials of customer
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = "application/json")
    public void logout(@RequestBody Token token) {
        securityService.logout(token.getValue());
    }
}
