package com.infosupport.t2c3.exceptions;

/**
 * Created by Stoux on 13/01/2016.
 */
public class ItemNotFoundException extends Exception {

    /**
     * Item not found exception constructor.
     *
     * @param message The message
     */
    public ItemNotFoundException(String message) {
        super(message);
    }

}
