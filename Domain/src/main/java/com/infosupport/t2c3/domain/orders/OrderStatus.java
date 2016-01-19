package com.infosupport.t2c3.domain.orders;

import lombok.Getter;

/**
 * The current status of an order.
 */
@Getter
public enum OrderStatus {

    PLACED,
    IN_PROGRESS,
    WAIT_FOR_APPROVAL,
    ACCEPTED,
    REJECTED,
    CANCELED,
    SENT

}
