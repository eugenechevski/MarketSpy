package com.eugenechevski.projects.MarketSpy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.azure.security.keyvault.secrets.SecretClient;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class MarketSpyApplication implements CommandLineRunner {
	// Spring Cloud Azure will automatically inject SecretClient in your
	// ApplicationContext.
	private final SecretClient secretClient;

	public MarketSpyApplication(SecretClient secretClient) {
		this.secretClient = secretClient;
	}

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.load();

		// Set system properties from .env file
		System.setProperty("SPRING-DATASOURCE-URL", dotenv.get("SPRING-DATASOURCE-URL"));
		System.setProperty("SPRING-DATASOURCE-USERNAME", dotenv.get("SPRING-DATASOURCE-USERNAME"));
		System.setProperty("SPRING-DATASOURCE-PASSWORD", dotenv.get("SPRING-DATASOURCE-PASSWORD"));
		System.setProperty("ALPHA-VANTAGE-API-KEY", dotenv.get("ALPHA-VANTAGE-API-KEY"));
		System.setProperty("AZURE-KEY-VAULT-URI", dotenv.get("AZURE-KEY-VAULT-URI"));

		SpringApplication.run(MarketSpyApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("ALPHA-VANTAGE-API-KEY: " + secretClient.getSecret("ALPHA-VANTAGE-API-KEY").getValue());
		
	}
}
