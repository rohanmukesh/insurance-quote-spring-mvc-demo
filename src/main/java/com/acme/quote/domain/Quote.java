package com.acme.quote.domain;

import java.math.BigDecimal;

public record Quote(
        BigDecimal coverage,
        RiskLevel risk,
        BigDecimal annualPremium) {
}
