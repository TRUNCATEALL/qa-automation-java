package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.dictionary.LoanResponseStatusType;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

/**
 * Loan repository
 */
public class SimpleCalcRepository implements LoanCalcRepository {
    private static int requestId;

    public static int getRequestId() {
        return requestId;
    }

    public static void setRequestId(int requestId) {
        SimpleCalcRepository.requestId = requestId;
    }

    /**
     * Процедура для сохранения id заявки
     *
     * @param loanRequest            - параметры заявки
     * @param loanResponseStatusType - решение по заявке
     * @return - LoanResponse - набор параметров (номер заявки + сама заявка + результат рассмотрения)
     */
    @Override
    public LoanResponse save(LoanRequest loanRequest, LoanResponseStatusType loanResponseStatusType) {
        return new LoanResponse(++requestId, loanRequest, loanResponseStatusType);
    }
}
