package com.infosupport.t2c3.domain.orders;

import lombok.Getter;

@Getter
public enum OrderStatus {

    PLACED("placed");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}