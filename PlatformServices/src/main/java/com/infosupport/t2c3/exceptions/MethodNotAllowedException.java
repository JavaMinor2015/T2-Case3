package com.infosupport.t2c3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Leon Stam on 19-1-2016.
 */
@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends CaseException {

    /**
     * Create a new MethodNotAllowedException.
     */
    public MethodNotAllowedException() {
        super("This method is not allowed.");
    }

}
