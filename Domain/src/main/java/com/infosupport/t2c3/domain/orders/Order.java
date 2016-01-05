package com.infosupport.t2c3.domain.orders;

import com.infosupport.t2c3.domain.customers.CustomerData;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class that combines all data to create an order.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    private BigDecimal totalPrice;
    private OrderStatus status;
    private List<OrderItem> items;
    private CustomerData customerData;

}
