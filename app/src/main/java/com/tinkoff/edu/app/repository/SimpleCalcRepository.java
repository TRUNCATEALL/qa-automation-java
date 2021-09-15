package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

/**
 * Loan repository
 */
public class SimpleCalcRepository implements LoanCalcRepository {

    /**
     * Процедура для сохранения id заявки
     *
     * @param loanRequest - параметры заявки
     * @return - UUID - номер заявки
     */

    public LoanResponse save(LoanRequest loanRequest, LoanResponseStatus loanResponseStatus) {
        //TODO: что-то будем делать с заявкой при сохранении в будущем
        return new LoanResponse(loanRequest.getRequestId(), loanRequest, loanResponseStatus);
    }
}
