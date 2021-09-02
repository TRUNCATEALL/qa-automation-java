package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

import java.util.UUID;

/**
 * Loan repository
 */
public class SimpleCalcRepository implements LoanCalcRepository {

    /**
     * Процедура для сохранения id заявки
     *
     * @param loanRequest        - параметры заявки
     * @param loanResponseStatus - решение по заявке
     * @return - LoanResponse - набор параметров (номер заявки + сама заявка + результат рассмотрения)
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest, LoanResponseStatus loanResponseStatus) {
        return new LoanResponse(UUID.randomUUID(), loanRequest, loanResponseStatus);
    }
}
