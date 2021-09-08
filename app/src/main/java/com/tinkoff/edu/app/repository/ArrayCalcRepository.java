package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.model.LoanRequest;

import java.util.UUID;

/**
 * Loan repository
 */
public class ArrayCalcRepository implements LoanCalcRepository {
    private LoanRequest[] requestsArray;
    private int currentIndex = 0;

    @Override
    public UUID save(LoanRequest loanRequest) {
        if (loanRequest == null)
            throw new NullPointerException("Данные по заявке отсутствуют");

        if (currentIndex >= requestsArray.length)
            throw new IllegalStateException("The loan repository is full");

        requestsArray[currentIndex++] = loanRequest;

        return UUID.randomUUID();
    }

    public ArrayCalcRepository() {
        requestsArray = new LoanRequest[5];
    }

    public int getMaxCapacity() {
        return requestsArray.length;
    }
}
