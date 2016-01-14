package com.infosupport.t2c3;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Created by Stoux on 13/01/2016.
 */
@EnableJpaRepositories("com.infosupport.t2c3.repositories")
@EntityScan("com.infosupport.t2c3.domain")
@ComponentScan("com.infosupport.t2c3.service")
@SpringBootApplication
@SuppressWarnings("squid:S1118")
public class Config {

}
