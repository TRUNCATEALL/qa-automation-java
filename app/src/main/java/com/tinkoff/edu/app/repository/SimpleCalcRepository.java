package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.dictionary.LoanResponseStatusType;

/**
 * Loan repository
 */
public class SimpleCalcRepository implements LoanCalcRepository{

    /**
     * Процедура для сохранения id заявки
     *
     * @param loanRequest - параметры заявки
     * @param loanResponseStatusType - решение по заявке
     * @return - LoanResponse - набор параметров (номер заявки + сама заявка + результат рассмотрения)
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest, LoanResponseStatusType loanResponseStatusType) {
        return new LoanResponse(loanRequest, loanResponseStatusType);
    }
}
