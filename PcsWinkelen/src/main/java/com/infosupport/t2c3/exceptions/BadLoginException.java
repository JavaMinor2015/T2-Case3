package com.infosupport.t2c3.exceptions;

/**
 * Created by Leon Stam on 19-1-2016.
 */
public class BadLoginException extends CaseException {

    /**
     * Create a BadLoginException.
     */
    public BadLoginException() {
        super("The given username + password combination is unknown/incorrect.");
    }

}
