package com.infosupport.t2c3.domain.abs;

import com.infosupport.t2c3.domain.rest.Linkable;
import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import lombok.Getter;
import org.springframework.hateoas.Identifiable;

/**
 * Created by Stoux on 06/01/2016.
 */
@Getter
@MappedSuperclass
public abstract class AbsEntity extends Linkable implements Serializable {

    private static final long serialVersionUID = 8645136395267215911L;

    @Id
    @GeneratedValue
    private Long id;

    @Version
    private int version;

}
