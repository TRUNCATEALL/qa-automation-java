package com.tinkoff.edu.app;

/**
 * Loan API
 */
public class LoanCalcController {

    public static int createRequest() {
        LoanCalcLogger.log();
        return LoanCalcService.createRequest();
    }
}
