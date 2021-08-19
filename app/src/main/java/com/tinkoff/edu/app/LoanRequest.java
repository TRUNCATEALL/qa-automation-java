package com.tinkoff.edu.app;

import java.math.BigDecimal;

public class LoanRequest {
    private final int months;
    private final BigDecimal amount;
    private final ClientType clientType;

    public LoanRequest(int months, BigDecimal amount, ClientType clientType) {
        this.months = months;
        this.amount = amount;
        this.clientType = clientType;
    }

    public ClientType getClientType() {
        return clientType;
    }

    public int getMonths() {
        return months;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String toString() {
        return "months: " + getMonths() + ", amount: " + getAmount() + ", clientType: " + getClientType();
    }
}
