package com.infosupport.t2c3.exceptions;

import java.math.BigDecimal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Leon Stam on 18-1-2016.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NoCreditException extends CaseException {

    public NoCreditException(BigDecimal maxCredit) {
        super("You have exceeded your max Credit limit of " + maxCredit.toPlainString());
    }

}
