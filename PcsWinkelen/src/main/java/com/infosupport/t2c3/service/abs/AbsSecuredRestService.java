package com.infosupport.t2c3.service.abs;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import com.infosupport.t2c3.domain.accounts.Customer;
import com.infosupport.t2c3.exceptions.InvalidTokenException;
import com.infosupport.t2c3.exceptions.UnauthorizedException;
import com.infosupport.t2c3.security.SecurityService;
import com.infosupport.t2c3.service.AbsRestService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * An Abstract RestService that has Security functions.
 *
 * @param <X> The type provided by this service
 */
public abstract class AbsSecuredRestService<X extends AbsEntity> extends AbsRestService<X> {

    @Autowired
    protected SecurityService securityService;

    /**
     * Get a customer by their token.
     *
     * @param token the token
     * @return the customer
     * @throws InvalidTokenException if the token is invalid
     */
    protected Customer getCustomer(String token) {
        Customer foundCustomer = securityService.getCustomerByToken(token);
        if (foundCustomer == null) {
            throw new InvalidTokenException();
        }
        return foundCustomer;
    }

    /**
     * Get a customer by their token.
     *
     * @param authorizedCustomerId expected Customer id
     * @param token the token
     * @return The customer
     * @throws InvalidTokenException if the token is invalid
     * @throws UnauthorizedException if a different customer id is expected
     */
    protected Customer getCustomer(long authorizedCustomerId, String token) {
        Customer foundCustomer = getCustomer(token);
        if (foundCustomer.getId().equals(authorizedCustomerId)) {
            return foundCustomer;
        } else {
            throw new UnauthorizedException();
        }
    }

}
