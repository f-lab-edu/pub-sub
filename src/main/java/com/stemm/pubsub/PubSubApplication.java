package com.stemm.pubsub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PubSubApplication {

	public static void main(String[] args) {
		SpringApplication.run(PubSubApplication.class, args);
	}

}
