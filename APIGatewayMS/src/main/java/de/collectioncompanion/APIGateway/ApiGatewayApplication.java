package de.collectioncompanion.APIGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiGatewayApplication {

	/**
	 * Port, on which each microservice can be used (basic addressing)
	 */
	// public static final int ROUTING_PORT = 8080;

	/**
	 * Port, on which each microservice can be used for gRPC (later)
	 */
	public static final int DB_SERVICE_PORT = 8081;

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
