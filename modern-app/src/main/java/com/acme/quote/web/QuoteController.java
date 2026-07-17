package com.acme.quote.web;

import com.acme.quote.domain.Quote;
import com.acme.quote.domain.RiskLevel;
import com.acme.quote.service.QuoteService;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuoteController {

	private final QuoteService quoteService;

	public QuoteController(QuoteService quoteService) {
		this.quoteService = quoteService;
	}

	@GetMapping("/api/quote")
	public Quote quote(
			@RequestParam BigDecimal coverage,
			@RequestParam RiskLevel risk) {
		return quoteService.calculate(coverage, risk);
	}
}
