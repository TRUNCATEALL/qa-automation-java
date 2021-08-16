package com.tinkoff.edu.app;

/**
 * Loan repository
 */
public class LoanCalcRepository {

    private static int requestId;

    /**
     * Процедура для сохранения id заявки
     * @return - Request ID
     */
    public static int save() {
        return requestId++;
    }
}
