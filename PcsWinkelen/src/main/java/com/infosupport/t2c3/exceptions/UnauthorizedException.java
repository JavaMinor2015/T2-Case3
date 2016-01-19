package com.infosupport.t2c3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Leon Stam on 19-1-2016.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends CaseException {

    /**
     * Create an UnauthorizedException.
     */
    public UnauthorizedException() {
        super("You're not authorized to access this.");
    }

}
