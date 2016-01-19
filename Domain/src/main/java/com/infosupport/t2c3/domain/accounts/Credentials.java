package com.infosupport.t2c3.domain.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infosupport.t2c3.domain.abs.AbsEntity;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(
        name = "CREDENTIALS",
        uniqueConstraints =
        @UniqueConstraint(columnNames = "USERNAME")
)
public class Credentials extends AbsEntity {

    private String userName;
    @JsonIgnore
    @Setter
    private String password;
    @Setter
    private String token;
}
