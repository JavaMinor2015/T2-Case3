package com.infosupport.t2c3.service;

import com.infosupport.t2c3.domain.customers.Customer;
import com.infosupport.t2c3.model.Token;
import com.infosupport.t2c3.security.SecurityService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Windows 7 on 12-1-2016.
 */
@RestController
@RequestMapping(produces = "application/json")
@CrossOrigin
@Setter
public class RegistrationService {

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/registrate", method = RequestMethod.POST, consumes = "application/json")
    public Token registrate(@RequestBody Customer customer) {
        String tokenValue = securityService.registrate(customer);
        Token token = new Token(tokenValue);
        return token;
    }

}
