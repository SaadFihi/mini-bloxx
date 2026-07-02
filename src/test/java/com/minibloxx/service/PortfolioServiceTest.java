package com.minibloxx.service;

import com.minibloxx.model.Portfolio;
import com.minibloxx.model.PortfolioValuation;
import com.minibloxx.model.Position;
import com.minibloxx.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Pure unit tests for PortfolioService.
 *
 * There is NO @SpringBootTest and NO Spring context here. We build the objects
 * by hand (new Repository, new Service), so these tests are tiny and fast and
 * exercise only our own logic — not Spring's wiring.
 *
 * A delta (0.01) is used on double assertions because floating-point math is
 * not exact; we assert "close enough", not bit-for-bit equality.
 */
class PortfolioServiceTest {

    private PortfolioService service;

    @BeforeEach
    void setUp() {
        // A fresh repository each test = fresh seeded data (ids 1 and 2).
        // Because each test starts clean, tests can't interfere with each other.
        PortfolioRepository repository = new PortfolioRepository();
        service = new PortfolioService(repository);
    }

    @Test
    @DisplayName("Creating a portfolio assigns it a non-null id")
    void createPortfolio_assignsNonNullId() {
        Portfolio created = service.createPortfolio(
                new Portfolio(null, "Carol Danvers", List.of(
                        new Position("NVIDIA Corp.", 20, 450.00, 520.75)
                ))
        );

        assertNotNull(created.getId());
    }

    @Test
    @DisplayName("A created portfolio is persisted and can be retrieved by its new id")
    void createPortfolio_isRetrievableById() {
        Portfolio created = service.createPortfolio(
                new Portfolio(null, "Carol Danvers", List.of(
                        new Position("NVIDIA Corp.", 20, 450.00, 520.75)
                ))
        );

        Portfolio fetched = service.getPortfolioById(created.getId());

        assertNotNull(fetched);
        assertEquals(created.getId(), fetched.getId());
        assertEquals("Carol Danvers", fetched.getClientName());
    }

    @Test
    @DisplayName("Creating two portfolios assigns two different, unique ids")
    void createPortfolio_assignsUniqueIds() {
        Portfolio first = service.createPortfolio(
                new Portfolio(null, "Carol Danvers", List.of(
                        new Position("NVIDIA Corp.", 20, 450.00, 520.75)
                ))
        );
        Portfolio second = service.createPortfolio(
                new Portfolio(null, "Dan Reed", List.of(
                        new Position("Tesla Inc.", 10, 240.00, 210.30)
                ))
        );

        assertNotEquals(first.getId(), second.getId());
    }

    @Test
    @DisplayName("Analytics for a portfolio computes total value, total cost, profit/loss and profit/loss percent")
    void getValuation_computesAnalyticsFromHandComputedPositions() {
        // Deliberately round numbers so we can compute the expected result by hand:
        //   Holding A: 10 * 150.00 = 1500 value, 10 * 100.00 = 1000 cost
        //   Holding B:  5 * 200.00 = 1000 value,  5 * 200.00 = 1000 cost
        //   totalValue = 2500, totalCost = 2000
        //   profitLoss = 500, percent = 500 / 2000 * 100 = 25.00%
        Portfolio created = service.createPortfolio(
                new Portfolio(null, "Test Client", List.of(
                        new Position("Holding A", 10, 100.00, 150.00),
                        new Position("Holding B", 5, 200.00, 200.00)
                ))
        );

        PortfolioValuation v = service.getValuation(created.getId());

        assertNotNull(v);
        assertEquals(2500.00, v.getTotalValue(), 0.01);
        assertEquals(2000.00, v.getTotalCost(), 0.01);
        assertEquals(500.00, v.getProfitLoss(), 0.01);
        assertEquals(25.00, v.getProfitLossPercent(), 0.01);
    }

    @Test
    @DisplayName("Analytics for a non-existent id returns null (current Service behavior)")
    void getValuation_unknownId_returnsNull() {
        // The current Service returns null for a missing id (it does not throw).
        assertNull(service.getValuation(999L));
    }

    @Test
    @DisplayName("Lookup by a non-existent id returns null (current Service behavior)")
    void getPortfolioById_unknownId_returnsNull() {
        // The current Service returns null for a missing id (it does not throw).
        assertNull(service.getPortfolioById(999L));
    }
}
