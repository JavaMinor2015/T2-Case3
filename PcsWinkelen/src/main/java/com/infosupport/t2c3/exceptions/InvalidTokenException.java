package com.infosupport.t2c3.exceptions;

/**
 * Created by Leon Stam on 19-1-2016.
 */
public class InvalidTokenException extends CaseException {

    /**
     * Create an InvalidTokenException.
     */
    public InvalidTokenException() {
        super("The given token is not valid.");
    }
}
