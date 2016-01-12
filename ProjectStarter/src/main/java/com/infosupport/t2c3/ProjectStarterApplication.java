package com.infosupport.t2c3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Application.
 */
@EnableJpaRepositories("com.infosupport.t2c3.repositories")
@EntityScan("com.infosupport.t2c3.domain")
@ComponentScan({"com.infosupport.t2c3.service", "com.infosupport.t2c3.security"})
@SpringBootApplication
@SuppressWarnings("squid:S1118")
public class ProjectStarterApplication {

    /**
     * Main method: Starts the application.
     *
     * @param args Possible command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(ProjectStarterApplication.class, args);
    }

}
