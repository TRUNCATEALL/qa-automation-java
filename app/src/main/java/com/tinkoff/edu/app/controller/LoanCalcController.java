package com.tinkoff.edu.app.controller;

import com.tinkoff.edu.app.logger.LoanCalcLogger;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.service.LoanCalcService;

/**
 * Loan API
 */
public class LoanCalcController {
    private final LoanCalcService loanCalcService;

    public LoanCalcController(LoanCalcService service) {
        this.loanCalcService = service;
    }

    public LoanResponse createRequest(LoanRequest loanRequest) {
        if (loanRequest == null)
            throw new NullPointerException("Данные по заявке отсутствуют");

        LoanCalcLogger.log();
        return loanCalcService.createRequest(loanRequest);
    }
}
