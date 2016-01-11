package com.infosupport.t2c3.domain.rest;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Transient;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.util.Assert;

/**
 * Created by Leon Stam on 11-1-2016.
 */
public abstract class Linkable implements Identifiable<Long> {

    /** List of Links that this class has. */
    private final List<Link> links = new ArrayList<>();

    /**
     * Add a link to this object.
     *
     * @param link The link
     */
    public void add(Link link) {
        Assert.notNull(link, "Link must not be null!");
        this.links.add(link);
    }

}
