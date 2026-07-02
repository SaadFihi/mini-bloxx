package com.minibloxx.controller;

import com.minibloxx.model.Portfolio;
import com.minibloxx.model.PortfolioValuation;
import com.minibloxx.service.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The web layer for portfolios. Translates HTTP requests into service calls
 * and turns the results back into JSON responses.
 *
 * @RequestMapping sets a common prefix so every method below starts at
 * "/api/portfolios".
 */
@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    // Constructor injection again — the controller depends on the service.
    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // GET /api/portfolios  -> all portfolios as JSON
    @GetMapping
    public List<Portfolio> getAllPortfolios() {
        return portfolioService.getAllPortfolios();
    }

    // GET /api/portfolios/{id}  -> one portfolio as JSON, or 404 if not found
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable Long id) {
        Portfolio portfolio = portfolioService.getPortfolioById(id);
        if (portfolio == null) {
            return ResponseEntity.notFound().build();  // HTTP 404
        }
        return ResponseEntity.ok(portfolio);           // HTTP 200 + JSON body
    }

    // GET /api/portfolios/{id}/analytics  -> computed valuation, or 404 if not found
    @GetMapping("/{id}/analytics")
    public ResponseEntity<PortfolioValuation> getPortfolioAnalytics(@PathVariable Long id) {
        PortfolioValuation valuation = portfolioService.getValuation(id);
        if (valuation == null) {
            return ResponseEntity.notFound().build();  // HTTP 404
        }
        return ResponseEntity.ok(valuation);           // HTTP 200 + JSON body
    }
}
