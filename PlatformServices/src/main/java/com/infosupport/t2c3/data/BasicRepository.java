package com.infosupport.t2c3.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * A interface that provides a simple way to create a repository
 *  with default features like findAll().
 *
 * @param <T> The class of the Model
 */
@Repository
public interface BasicRepository<T> extends JpaRepository<T, Long> {

}
