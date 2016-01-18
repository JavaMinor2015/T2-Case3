package com.infosupport.t2c3.exceptions;

/**
 * Created by Stoux on 13/01/2016.
 */
public class NonUniqueValueException extends CaseException {

    /**
     * Item not found exception constructor.
     *
     * @param message The message
     */
    public NonUniqueValueException(String message) {
        super(message);
    }

}
