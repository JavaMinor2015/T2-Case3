package com.infosupport.t2c3.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Stoux on 13/01/2016.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ItemNotFoundException extends CaseException {

    /**
     * Item not found exception constructor.
     *
     * @param message The message
     */
    public ItemNotFoundException(String message) {
        super(message);
    }

}
