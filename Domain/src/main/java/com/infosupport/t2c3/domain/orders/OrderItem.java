package com.infosupport.t2c3.domain.orders;

import com.infosupport.t2c3.domain.products.Product;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    private BigDecimal price;
    private int amount;
    private Product product;
}
