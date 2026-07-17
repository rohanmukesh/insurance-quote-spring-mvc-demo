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

		assertThat(quote.coverage()).isEqualByComparingTo("100000");
		assertThat(quote.risk()).isEqualTo(RiskLevel.LOW);
		assertThat(quote.annualPremium()).isEqualByComparingTo("2000.00");
	}

	@Test
	void calculatesHighRiskPremium() {
		Quote quote = service.calculate(new BigDecimal("100000"), RiskLevel.HIGH);

		assertThat(quote.annualPremium()).isEqualByComparingTo("3200.00");
	}

	@Test
	void calculatesMediumRiskPremium() {
		Quote quote = service.calculate(new BigDecimal("100000"), RiskLevel.MEDIUM);

		assertThat(quote.annualPremium()).isEqualByComparingTo("2500.00");
	}

	@Test
	void rejectsZeroCoverage() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> service.calculate(BigDecimal.ZERO, RiskLevel.MEDIUM))
				.withMessage("Coverage must be greater than zero");
	}

	@Test
	void rejectsNegativeCoverage() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> service.calculate(BigDecimal.ONE.negate(), RiskLevel.LOW))
				.withMessage("Coverage must be greater than zero");
	}

	@Test
	void rejectsNullRisk() {
		assertThatIllegalArgumentException()
				.isThrownBy(() -> service.calculate(new BigDecimal("100000"), null))
				.withMessage("Risk level is required");
	}

	@Test
	void roundsPremiumToTwoDecimalPlaces() {
		Quote quote = service.calculate(new BigDecimal("1000.005"), RiskLevel.LOW);

		assertThat(quote.annualPremium()).isEqualByComparingTo("20.00");
	}
}
