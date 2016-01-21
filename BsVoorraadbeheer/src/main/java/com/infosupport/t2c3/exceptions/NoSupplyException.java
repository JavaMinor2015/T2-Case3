package com.infosupport.t2c3.exceptions;

import com.infosupport.t2c3.domain.products.Product;
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
     * @param product The product in question
     * @param itemsLeft The actual number of items left
     */
    public NoSupplyException(Product product, int itemsLeft) {
        super(
                "There are only " + itemsLeft + " items left of " + product.getName()
        );
        this.itemsLeft = itemsLeft;
    }

}
