package com.infosupport.t2c3.domain.orders;

import lombok.Getter;

/**
 * The current status of an order.
 */
@Getter
public enum OrderStatus {

    WAIT_FOR_APPROVE,
    PLACED,
    REJECTED,
    SENT

}
