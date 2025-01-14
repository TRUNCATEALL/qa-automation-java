package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

import java.util.UUID;

/**
 * Loan repository
 */
public class ArrayCalcRepository implements LoanCalcRepository {
    private LoanRequest[] requestsArray;
    private int currentIndex = 0;

    @Override
    public boolean save(LoanRequest loanRequest, LoanResponse loanResponse) {
        if (loanRequest == null)
            throw new NullPointerException("Данные по заявке отсутствуют");

        if (currentIndex >= requestsArray.length)
            throw new IllegalStateException("The loan repository is full");

        requestsArray[currentIndex++] = loanRequest;

        return true;
    }

    public ArrayCalcRepository() {
        requestsArray = new LoanRequest[5];
    }

    public int getMaxCapacity() {
        return requestsArray.length;
    }
}
