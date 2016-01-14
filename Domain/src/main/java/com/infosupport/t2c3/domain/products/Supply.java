package com.infosupport.t2c3.domain.products;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Stoux on 13/01/2016.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Supply extends AbsEntity {

    private static final long serialVersionUID = -6514614078277487241L;

    @OneToOne(cascade = CascadeType.PERSIST)
    private Product product;
    @Setter
    private int left;

    private String supplier;

}
