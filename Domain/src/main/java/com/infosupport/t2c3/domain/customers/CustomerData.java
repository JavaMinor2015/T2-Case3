package com.infosupport.t2c3.domain.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * General customer data.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerData {

    private String firstName;
    private String lastName;
    private String emailAddress;
    private Address address;

}
