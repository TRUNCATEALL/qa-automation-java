package com.tinkoff.edu.app;

/**
 * Loan repository
 */
public class LoanCalcRepository {

    /**
     * Процедура для сохранения id заявки
     *
     * @param loanRequest - параметры заявки
     * @param loanResponseStatusType - решение по заявке
     * @return - LoanResponse - набор параметров (номер заявки + сама заявка + результат рассмотрения)
     */
    public LoanResponse save(LoanRequest loanRequest, LoanResponseStatusType loanResponseStatusType) {
        return new LoanResponse(loanRequest, loanResponseStatusType);
    }
}
