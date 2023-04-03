package com.bill.finmark.assignment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableJpaRepositories
@EnableCaching
public class FinMarkMovieCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinMarkMovieCrudApplication.class, args);
	}

}
