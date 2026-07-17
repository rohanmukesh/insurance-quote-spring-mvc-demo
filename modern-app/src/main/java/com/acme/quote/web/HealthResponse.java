package com.acme.quote.web;

public record HealthResponse(String status) {

	public static HealthResponse up() {
		return new HealthResponse("UP");
	}
}
