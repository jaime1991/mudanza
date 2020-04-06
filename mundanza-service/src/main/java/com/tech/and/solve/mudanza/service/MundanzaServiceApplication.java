package com.tech.and.solve.mudanza.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.event.LoggingEventListener;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@EntityScan("com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.model")
@EnableReactiveMongoRepositories("com.tech.and.solve.mudanza.service.infraestructure.persistence.mongo.repository")
@SpringBootApplication(scanBasePackages = "com.tech.and.solve.mudanza.service")
public class MundanzaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MundanzaServiceApplication.class, args);
	}
	
	@Bean
	public LoggingEventListener mongoEventListener() {
		return new LoggingEventListener();
	}

}
