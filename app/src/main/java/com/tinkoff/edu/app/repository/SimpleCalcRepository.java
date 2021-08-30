package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

/**
 * Loan repository
 */
public class SimpleCalcRepository implements LoanCalcRepository {
    private int requestId;

    /**
     * Процедура для сохранения id заявки
     *
     * @param loanRequest            - параметры заявки
     * @param loanResponseStatus - решение по заявке
     * @return - LoanResponse - набор параметров (номер заявки + сама заявка + результат рассмотрения)
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest, LoanResponseStatus loanResponseStatus) {
        return new LoanResponse(++requestId, loanRequest, loanResponseStatus);
    }
}
