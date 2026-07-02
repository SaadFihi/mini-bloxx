package com.minibloxx.model;

/**
 * A single holding inside a portfolio: how much of one instrument we own,
 * what we paid for it, and what it is worth right now.
 *
 * This is a "plain old Java object" (POJO) — just data, no Spring, no logic.
 */
public class Position {

    private String instrument;    // e.g. "Apple Inc." or "MSCI World ETF"
    private int quantity;         // how many units/shares we hold
    private double buyPrice;      // price per unit when we bought
    private double currentPrice;  // price per unit today

    // Constructor: the only way to build a Position, forcing all fields to be set.
    public Position(String instrument, int quantity, double buyPrice, double currentPrice) {
        this.instrument = instrument;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.currentPrice = currentPrice;
    }

    // Getters: read-only access to each field. Jackson uses these to build JSON.
    public String getInstrument() {
        return instrument;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getBuyPrice() {
        return buyPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }
}
