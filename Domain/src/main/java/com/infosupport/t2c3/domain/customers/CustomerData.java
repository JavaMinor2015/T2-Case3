package com.infosupport.t2c3.domain.customers;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * General customer data.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerData extends AbsEntity {

    private static final long serialVersionUID = -6108420221541643144L;

    private String firstName;
    private String lastName;
    private String emailAddress;
    @OneToOne
    private Address address;

}
