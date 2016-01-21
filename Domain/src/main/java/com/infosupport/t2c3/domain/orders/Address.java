package com.infosupport.t2c3.domain.orders;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Address data.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends AbsEntity {

    private static final long serialVersionUID = 984528033434820845L;

    private String city;
    private String street;
    private String streetNumber;
    private String zipcode;

    /**
     * Edit the editable fields.
     * @param address address object with the new values
     */
    public void edit(Address address) {
        this.city = address.getCity();
        this.street = address.getStreet();
        this.streetNumber = address.getStreetNumber();
        this.zipcode = address.getZipcode();
    }
}
