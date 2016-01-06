package com.infosupport.t2c3.domain.abs;

import java.io.Serializable;
import javax.persistence.*;
import lombok.Getter;

/**
 * Created by Stoux on 06/01/2016.
 */
@Getter
@MappedSuperclass
public abstract class AbsEntity implements Serializable {

    private static final long serialVersionUID = 8645136395267215911L;

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

}
