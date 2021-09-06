package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.model.LoanRequest;

import java.util.UUID;

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

    public UUID save(LoanRequest loanRequest) {
        //TODO: что-то будем делать с заявкой при сохранении в будущем
        return UUID.randomUUID();
    }
}
