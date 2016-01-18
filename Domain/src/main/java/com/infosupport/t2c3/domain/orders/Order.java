package com.infosupport.t2c3.domain.orders;

import com.infosupport.t2c3.domain.abs.AbsVaultEntity;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Class that combines all data to create an order.
 *
 * Override default table name as ORDER is a reserved command.
 */
@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDERS")
public class Order extends AbsVaultEntity {

    private static final long serialVersionUID = 3562382393047819011L;

    @Setter
    private BigDecimal totalPrice;
    @Enumerated(EnumType.STRING)
    @Setter
    private OrderStatus status;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<OrderItem> items;
    @OneToOne(cascade = CascadeType.PERSIST)
    private CustomerData customerData;

    @Override
    public String generateBusinessKey() {
        return "ORD-" + getId();
    }
}
