package com.infosupport.t2c3.domain.orders;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import com.infosupport.t2c3.domain.products.Product;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A single item in a order.
 *
 * Contains the number of times a certain product
 *  is in the order.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class OrderItem extends AbsEntity {

    private static final long serialVersionUID = 5095267107708465278L;

    /** The price of a single item. */
    @Setter
    private BigDecimal price;

    private int amount;

    @Setter
    @ManyToOne
    private Product product;
}
