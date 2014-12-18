package com.sweroad.query;

/**
 * Created by Frank on 12/18/14.
 */
public enum Comparison {
    EQ(" = "),
    NE(" <> "),
    GT(" > "),
    GTE(" >= "),
    LT(" < "),
    LTE(" <= "),
    IN(" in ");

    Comparison(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;

    public String getSymbol() {
        return this.symbol;
    }
}
