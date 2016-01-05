package com.infosupport.t2c3.domain.products;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private String name;
    private BigDecimal price;
    private Category category;
    private String supplier;
    private boolean available;
    private String imageURL;


}
