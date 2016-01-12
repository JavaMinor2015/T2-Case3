package com.infosupport.t2c3.domain.customers;

import com.infosupport.t2c3.domain.abs.AbsEntity;
import javax.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Windows 7 on 11-1-2016.
 */
@Entity
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class Credentials extends AbsEntity {

    private String userName;
    private String password;
    @Setter
    private String token;
}
