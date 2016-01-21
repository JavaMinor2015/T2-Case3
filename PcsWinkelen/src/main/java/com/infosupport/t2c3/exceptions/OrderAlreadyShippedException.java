package com.infosupport.t2c3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Leon Stam on 18-1-2016.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class OrderAlreadyShippedException extends CaseException {

    /**
     * Create a OrderAlreadyShippedException.
     */
    public OrderAlreadyShippedException() {
        super("The order you want to edit has already been sent.");
    }

}
