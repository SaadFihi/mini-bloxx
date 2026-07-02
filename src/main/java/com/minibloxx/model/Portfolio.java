package com.minibloxx.model;

import java.util.List;

/**
 * A client's portfolio: an id, whose it is, and the positions it contains.
 * Also a plain data object (POJO) — no Spring annotations here.
 */
public class Portfolio {

    private Long id;                   // unique identifier for this portfolio
    private String clientName;         // who owns it
    private List<Position> positions;  // the holdings inside it

    public Portfolio(Long id, String clientName, List<Position> positions) {
        this.id = id;
        this.clientName = clientName;
        this.positions = positions;
    }

    public Long getId() {
        return id;
    }

    public String getClientName() {
        return clientName;
    }

    public List<Position> getPositions() {
        return positions;
    }
}
