package com.infosupport.t2c3.domain.orders;

import com.infosupport.t2c3.domain.products.Product;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A single item in a order.
 *
 * Contains the number of times a certain product
 *  is in the order.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    /** The price of a single item. */
    private BigDecimal price;
    private int amount;
    private Product product;
}
