package com.minibloxx.service;

import com.minibloxx.model.Portfolio;
import com.minibloxx.model.PortfolioValuation;
import com.minibloxx.model.Position;
import com.minibloxx.repository.PortfolioRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The business-logic layer for portfolios.
 *
 * Retrieval methods delegate to the repository. getValuation() is where the
 * first REAL business logic lives: it derives analytics from the raw data.
 */
@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;

    // Constructor injection: Spring passes in the PortfolioRepository bean here.
    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public Portfolio getPortfolioById(Long id) {
        return portfolioRepository.findById(id);
    }

    /**
     * Computes the analytics for one portfolio.
     * Returns null if no portfolio has the given id (same contract as findById).
     */
    public PortfolioValuation getValuation(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id);
        if (portfolio == null) {
            return null;
        }

        double totalValue = 0.0;
        double totalCost = 0.0;

        // A plain for-each loop: readable, and easy to step through in a debugger.
        for (Position position : portfolio.getPositions()) {
            totalValue += position.getQuantity() * position.getCurrentPrice();
            totalCost  += position.getQuantity() * position.getBuyPrice();
        }

        double profitLoss = totalValue - totalCost;

        // Guard against divide-by-zero: an empty/zero-cost portfolio has 0% P/L.
        double profitLossPercent = 0.0;
        if (totalCost != 0.0) {
            profitLossPercent = (profitLoss / totalCost) * 100;
        }

        // Round the percentage to 2 decimal places (e.g. 12.3456 -> 12.35).
        profitLossPercent = Math.round(profitLossPercent * 100.0) / 100.0;

        return new PortfolioValuation(
                portfolio.getId(),
                portfolio.getClientName(),
                totalValue,
                totalCost,
                profitLoss,
                profitLossPercent
        );
    }
}
