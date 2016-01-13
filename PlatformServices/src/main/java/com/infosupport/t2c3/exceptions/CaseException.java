package com.infosupport.t2c3.exceptions;

/**
 * Created by Stoux on 13/01/2016.
 */
public abstract class CaseException extends Exception {

    /**
     * Create a basic CaseException.
     *
     * @param message The message
     */
    public CaseException(String message) {
        super(message);
    }
}
