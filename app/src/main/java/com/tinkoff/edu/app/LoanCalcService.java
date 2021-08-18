package com.tinkoff.edu.app;

import java.math.BigDecimal;

/**
 * Loan calculation
 */
public class LoanCalcService {

    public LoanResponse createRequest(LoanRequest loanRequest) {
        BigDecimal maxAmount = new BigDecimal(100000);
        BigDecimal devilAmount = new BigDecimal(666);
        LoanResponseStatusType loanResponse = LoanResponseStatusType.APPROVED;

        if (loanRequest.getAmount().compareTo(maxAmount) > 0) {
            loanResponse = LoanResponseStatusType.DENIED;
        } else if (loanRequest.getAmount().compareTo(devilAmount) == 0) {
            loanResponse = LoanResponseStatusType.ERROR;
        }

        if (loanRequest.getClientType() == ClientType.INC) {
            loanResponse = LoanResponseStatusType.DENIED;
        }

        return new LoanCalcRepository().save(loanRequest, loanResponse);
    }
}
