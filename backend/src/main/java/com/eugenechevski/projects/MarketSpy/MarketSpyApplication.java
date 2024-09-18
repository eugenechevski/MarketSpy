package com.eugenechevski.projects.MarketSpy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MarketSpyApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		 // Set system properties from .env file
		 System.setProperty("SPRING_DATASOURCE_URL", dotenv.get("SPRING_DATASOURCE_URL"));

		System.out.println("SPRING_DATASOURCE_URL: " + System.getProperty("SPRING_DATASOURCE_URL"));

		SpringApplication.run(MarketSpyApplication.class, args);
	}

}
