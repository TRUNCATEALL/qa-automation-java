package com.tinkoff.edu.app;

/**
 * Loan API
 */
public class LoanCalcController {

    public LoanResponse createRequest(LoanRequest loanRequest) {
        LoanCalcLogger.log();
        return new LoanCalcService().createRequest(loanRequest);
    }
}
