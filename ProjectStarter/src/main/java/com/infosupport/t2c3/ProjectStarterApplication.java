package com.infosupport.t2c3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Spring Application.
 */
@SpringBootApplication()
@ComponentScan({"com.infosupport.t2c3.service"})
public class ProjectStarterApplication {

    /**
     * Main method: Starts the application.
     * @param args Possible command line arguments
     */
	public static void main(String[] args) {
		SpringApplication.run(ProjectStarterApplication.class, args);
	}

}
