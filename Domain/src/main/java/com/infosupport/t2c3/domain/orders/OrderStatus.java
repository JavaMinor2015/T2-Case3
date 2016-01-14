package com.infosupport.t2c3.domain.orders;

import lombok.Getter;

/**
 * The current status of an order.
 */
@Getter
public enum OrderStatus {

    PLACED("placed"),
    REJECTED("rejected"),
    SENT("sent");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }

}
