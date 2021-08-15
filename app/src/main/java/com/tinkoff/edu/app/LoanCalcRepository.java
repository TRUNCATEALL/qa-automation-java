package com.tinkoff.edu.app;

/**
 * Loan repository
 */
public class LoanCalcRepository {

    private static int requestId;

    /**
     * Procedure for save requestId
     * @return - Request ID
     */
    public static int save() {
        int result = ++requestId;
        return result;
    }
}
