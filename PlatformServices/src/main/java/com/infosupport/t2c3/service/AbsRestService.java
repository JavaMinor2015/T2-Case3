package com.infosupport.t2c3.service;

import com.infosupport.t2c3.data.BasicRepository;
import com.infosupport.t2c3.domain.abs.AbsEntity;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ws.rs.Produces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * An Abstract RestService that contains a couple of
 *  usefull methods for basic REST usage.
 *
 * @param <X> The type provided by this service
 */
@CrossOrigin
@Produces("application/json")
public abstract class AbsRestService<X extends AbsEntity> {

    /** The repo that is used for basic REST calls. */
    private BasicRepository<X> repo;

    /**
     * Get an entity by it's ID.
     *
     * @param id the id
     * @return the value or null
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public X getById(@PathVariable("id") final long id) {
        return repo.findOne(id);
    }

    /**
     * Get all entities.
     *
     * @return list of entities
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<X> getAll() {
        return repo.findAll();
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

}
