package com.minibloxx.repository;

import com.minibloxx.model.Portfolio;
import com.minibloxx.model.Position;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Stores portfolios in memory (no database yet).
 *
 * We use a Map keyed by the portfolio id so findById is a fast, direct lookup.
 * LinkedHashMap keeps insertion order, so findAll() returns them in a stable order.
 */
@Repository
public class PortfolioRepository {

    private final Map<Long, Portfolio> portfolios = new LinkedHashMap<>();

    // Runs once when Spring creates this bean at startup: seed some sample data.
    public PortfolioRepository() {
        Portfolio p1 = new Portfolio(1L, "Alice Johnson", List.of(
                new Position("Apple Inc.", 50, 150.00, 172.50),
                new Position("Microsoft Corp.", 30, 280.00, 315.20)
        ));

        Portfolio p2 = new Portfolio(2L, "Bob Smith", List.of(
                new Position("MSCI World ETF", 100, 85.40, 92.10),
                new Position("Amazon.com Inc.", 15, 120.00, 133.75)
        ));

        portfolios.put(p1.getId(), p1);
        portfolios.put(p2.getId(), p2);
    }

    /** Returns every portfolio we have. */
    public List<Portfolio> findAll() {
        return new ArrayList<>(portfolios.values());
    }

    /** Returns the portfolio with the given id, or null if none exists. */
    public Portfolio findById(Long id) {
        return portfolios.get(id);
    }
}
