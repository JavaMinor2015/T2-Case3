package com.infosupport.t2c3.domain.orders;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * General customer data.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class CustomerData extends AbsEntity {

    private static final long serialVersionUID = -6108420221541643144L;

    private String firstName;
    private String lastName;
    private String emailAddress;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Address address;

}
