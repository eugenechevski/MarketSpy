package com.eugenechevski.projects.MarketSpy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

import com.azure.security.keyvault.secrets.SecretClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketSpyApplication implements CommandLineRunner{
	// Spring Cloud Azure will automatically inject SecretClient in your ApplicationContext.
	private final SecretClient secretClient;

	public MarketSpyApplication(SecretClient secretClient) {
		this.secretClient = secretClient;
	}

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		 // Set system properties from .env file
		 System.setProperty("SPRING-DATASOURCE-URL", dotenv.get("SPRING-DATASOURCE-URL"));

		System.out.println("SPRING-DATASOURCE-URL: " + System.getProperty("SPRING-DATASOURCE-URL"));

		SpringApplication.run(MarketSpyApplication.class, args);
	}

	@Override
    public void run(String... args) {
        System.out.println("ALPHA-VANTAGE-API-KEY: " + secretClient.getSecret("ALPHA-VANTAGE-API-KEY").getValue());
    }
}
