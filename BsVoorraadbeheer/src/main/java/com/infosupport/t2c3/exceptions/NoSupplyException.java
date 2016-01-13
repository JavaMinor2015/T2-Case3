package com.infosupport.t2c3.exceptions;

import lombok.Getter;

/**
 * Created by Stoux on 13/01/2016.
 */
@Getter
public class NoSupplyException extends Exception {

    private int itemsLeft;

    public NoSupplyException(int itemsLeft) {
        super("There are only " + itemsLeft + " items left.");
        this.itemsLeft = itemsLeft;
    }

}
