package com.acme.quote.domain;

import java.math.BigDecimal;

public enum RiskLevel {
	LOW("1.00"),
	MEDIUM("1.25"),
	HIGH("1.60");

	private final BigDecimal factor;

	RiskLevel(String factor) {
		this.factor = new BigDecimal(factor);
	}

	public BigDecimal factor() {
		return factor;
	}
}
