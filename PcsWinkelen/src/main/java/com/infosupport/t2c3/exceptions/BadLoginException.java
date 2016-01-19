package com.infosupport.t2c3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Leon Stam on 19-1-2016.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class BadLoginException extends CaseException {

    /**
     * Create a BadLoginException.
     */
    public BadLoginException() {
        super("The given username + password combination is unknown/incorrect.");
    }

}
