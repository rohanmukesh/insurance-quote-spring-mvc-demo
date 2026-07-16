package com.acme.quote.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import com.acme.quote.domain.Quote;
import com.acme.quote.domain.RiskLevel;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class QuoteServiceTest {

    private final QuoteService service = new QuoteService();

    @Test
    void calculatesLowRiskPremium() {
        Quote quote = service.calculate(new BigDecimal("100000"), RiskLevel.LOW);

        assertThat(quote.annualPremium()).isEqualByComparingTo("2000.00");
    }

    @Test
    void appliesHighRiskFactor() {
        Quote quote = service.calculate(new BigDecimal("100000"), RiskLevel.HIGH);

        assertThat(quote.annualPremium()).isEqualByComparingTo("3200.00");
    }

    @Test
    void rejectsNonPositiveCoverage() {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> service.calculate(BigDecimal.ZERO, RiskLevel.MEDIUM))
                .withMessage("Coverage must be greater than zero");
    }
}
