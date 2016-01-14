package com.infosupport.t2c3.model;

import com.infosupport.t2c3.domain.orders.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Windows 7 on 13-1-2016.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequest {
    private Token token;
    private Order order;

}
