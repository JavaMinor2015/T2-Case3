package com.infosupport.t2c3.model;

import com.infosupport.t2c3.domain.customers.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by Windows 7 on 12-1-2016.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    private String value;
    private Customer customer;
}
