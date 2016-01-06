package com.infosupport.t2c3.domain.products;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A product.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private long ID;
    private String name;
    private BigDecimal price;
    private Category category;
    private String supplier;
    private boolean available;
    private String imageURL;

}
