package com.acme.quote.service;

import com.acme.quote.domain.Quote;
import com.acme.quote.domain.RiskLevel;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Service;

@Service
public class QuoteService {

    private static final BigDecimal BASE_RATE = new BigDecimal("0.02");

    public Quote calculate(BigDecimal coverage, RiskLevel risk) {
        if (coverage == null || coverage.signum() <= 0) {
            throw new IllegalArgumentException("Coverage must be greater than zero");
        }
        if (risk == null) {
            throw new IllegalArgumentException("Risk level is required");
        }

        BigDecimal annualPremium = coverage
                .multiply(BASE_RATE)
                .multiply(risk.factor())
                .setScale(2, RoundingMode.HALF_UP);
        return new Quote(coverage, risk, annualPremium);
    }
}
