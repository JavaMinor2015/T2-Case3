package com.infosupport.t2c3.exceptions;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Stoux on 13/01/2016.
 */
@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoSupplyException extends CaseException {

    private final int itemsLeft;

    /**
     * Constructor for a NoSupplyException.
     *
     * @param ofEntity The entity in question
     * @param itemsLeft The actual number of items left
     */
    public NoSupplyException(AbsEntity ofEntity, int itemsLeft, String name) {
        super(
                "There are only " + itemsLeft + " items left of " + name
        );
        this.itemsLeft = itemsLeft;
    }

}
