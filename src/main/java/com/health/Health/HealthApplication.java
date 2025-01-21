package com.health.Health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.health.Health"})
public class HealthApplication {
	public static void main(String[] args) {
		SpringApplication.run(HealthApplication.class, args);
	}
}
