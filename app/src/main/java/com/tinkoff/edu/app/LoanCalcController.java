package com.tinkoff.edu.app;

/**
 * Loan API
 */
public class LoanCalcController {
    private final LoanCalcService loanCalcService;

    public LoanCalcController() {
        loanCalcService = new LoanCalcService();
    }

    public LoanResponse createRequest(LoanRequest loanRequest) {
        LoanCalcLogger.log();
        return loanCalcService.createRequest(loanRequest);
    }
}
