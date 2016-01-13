package com.infosupport.t2c3.repositories;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.customers.Customer;
import org.springframework.stereotype.Component;

/**
 * Created by Windows 7 on 06/01/2016.
 */
@Component
public interface CustomerRepository extends BasicRepository<Customer> {

    /**
     * Find customer by username of credentials.
     * @param userName the username
     * @return the customer
     */
    Customer findByCredentialsUserName(String userName);

    /**
     * Find customer by token of credentials.
     * @param token the token
     * @return the customer
     */
    Customer findByCredentialsToken(String token);
}
