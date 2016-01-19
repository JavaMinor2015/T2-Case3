package com.infosupport.t2c3.domain.products;

import com.infosupport.t2c3.domain.abs.AbsVaultEntity;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * A product.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product extends AbsVaultEntity {

    private static final long serialVersionUID = 6547550483593301174L;

    private String name;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Category category;
    private String imageURL;

    @Override
    public String generateBusinessKey() {
        return "PROD-" + asThreeDigits(category.toString()) + "-" + getId();
    }

}
