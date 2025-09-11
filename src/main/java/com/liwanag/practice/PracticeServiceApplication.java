package com.liwanag.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Note: instead of the classic controller-service-repository Spring setup, I've chosen to structure
// the Practice microservice with Hexagonal Architecture (Ports and Adapters).

// I have chosen this architecture based on two factors:
// 1. there are multiple external integrations (e.g. DynamoDB, S3) and I want to swap them out
// with mocks for easier unit testing
// 2. classic architecture (controller directly invokes service) is brittle to changes, esp. when new architecture
// and features are added like ML answer evaluation or question generation

// Hexagonal Architecture solves the above issues by isolating core business logic from databases or external APIs;
// logic is independent of technological choices. Additionally, as long as adapters adhere to the defined
// ports/interfaces, adapters can be swapped out easily for business logic to invoke.

// Note: only adapters should use Spring annotations

// Sources:
// https://dev.to/jhonifaber/hexagonal-architecture-or-port-adapters-23ed
// https://www.baeldung.com/hexagonal-architecture-ddd-spring
@SpringBootApplication
public class PracticeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticeServiceApplication.class, args);
	}

}
