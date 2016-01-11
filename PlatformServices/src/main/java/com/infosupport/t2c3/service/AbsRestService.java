package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.abs.AbsEntity;
import com.infosupport.t2c3.domain.rest.Linkable;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * An Abstract RestService that contains a couple of
 *  usefull methods for basic REST usage.
 *
 * @param <X> The type provided by this service
 */
@CrossOrigin
public abstract class AbsRestService<X extends AbsEntity> {

    /** The repo that is used for basic REST calls. */
    private BasicRepository<X> repo;

    /** Util class that provides Links for multiple classes. */
    @Autowired
    protected EntityLinks entityLinks;

    /**
     * Get an entity by it's ID.
     *
     * @param id the id
     * @return the value or null
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public X getById(@PathVariable("id") final long id) {
        return addSelfLink(
                repo.findOne(id)
        );
    }

    /**
     * Get all entities.
     *
     * @return list of entities
     */
    @RequestMapping(method = RequestMethod.GET)
    public Resources<X> getAll() {
        Resources<X> resources = new Resources<>(repo.findAll());
        resources.forEach(this::addSelfLink);
        resources.add(linkTo(methodOn(getClass()).getAll()).withSelfRel());
        return resources;
    }

    /**
     * A PostConstruct call that fills all the values
     *  in this abstract service.
     */
    @PostConstruct
    public void fillValues() {
        this.repo = provideRepo();
    }

    /**
     * Provide a repo for this rest service.
     *
     * This is called after construction through
     *  the post construct #fillValues().
     * @return the repo
     */
    public abstract BasicRepository<X> provideRepo();

    /**
     * Add a self link to a Linkable Object that implements Identifiable and is registered
     *  with EntityLinks.
     *
     * @param item The item
     * @param <Z> The class of the item
     * @return The same item for stringing
     */
    protected <Z extends Linkable> Z addSelfLink(Z item) {
        item.add(entityLinks.linkToSingleResource(item));
        return item;
    }

}
