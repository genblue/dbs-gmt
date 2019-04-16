package com.save.mangrove;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class DltMangroveApplication {

	public static void main(String[] args) {

		SpringApplication.run(DltMangroveApplication.class, args);
	}

	protected SpringApplicationBuilder configure(SpringApplicationBuilder application)
    {
        return application.sources(DltMangroveApplication.class);
    }
}
