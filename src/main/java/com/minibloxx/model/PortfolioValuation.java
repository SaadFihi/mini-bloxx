package com.minibloxx.model;

/**
 * The computed analytics for one portfolio.
 *
 * Note this is a SEPARATE class from Portfolio/Position — we do not store these
 * numbers on the entities themselves (that would let stored data contradict the
 * facts). The service builds one of these on demand and hands it back.
 *
 * It lives in the model package as a plain class with a constructor and getters,
 * matching the style of Portfolio and Position.
 */
public class PortfolioValuation {

    private Long portfolioId;
    private String clientName;
    private double totalValue;         // sum of quantity * currentPrice
    private double totalCost;          // sum of quantity * buyPrice
    private double profitLoss;         // totalValue - totalCost
    private double profitLossPercent;  // (profitLoss / totalCost) * 100

    public PortfolioValuation(Long portfolioId, String clientName, double totalValue,
                              double totalCost, double profitLoss, double profitLossPercent) {
        this.portfolioId = portfolioId;
        this.clientName = clientName;
        this.totalValue = totalValue;
        this.totalCost = totalCost;
        this.profitLoss = profitLoss;
        this.profitLossPercent = profitLossPercent;
    }

    public Long getPortfolioId()          { return portfolioId; }
    public String getClientName()         { return clientName; }
    public double getTotalValue()         { return totalValue; }
    public double getTotalCost()          { return totalCost; }
    public double getProfitLoss()         { return profitLoss; }
    public double getProfitLossPercent()  { return profitLossPercent; }
}
