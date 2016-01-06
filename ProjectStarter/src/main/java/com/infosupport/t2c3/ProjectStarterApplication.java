package com.infosupport.t2c3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Spring Application.
 */
@EnableJpaRepositories("com.infosupport.t2c3.repositories")
@EntityScan("com.infosupport.t2c3.domain")
@SpringBootApplication(scanBasePackages = "com.infosupport.t2c3")
public class ProjectStarterApplication {

    /**
     * Main method: Starts the application.
     * @param args Possible command line arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(ProjectStarterApplication.class, args);
	}

}
