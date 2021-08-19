package com.tinkoff.edu.app;

import java.math.BigDecimal;

/**
 * Loan calculation
 */
public class LoanCalcService {
    private final LoanCalcRepository loanCalcRepository;

    public LoanCalcService() {
        loanCalcRepository = new LoanCalcRepository();
    }

    public LoanResponse createRequest(LoanRequest loanRequest) {
        BigDecimal maxAmount = new BigDecimal(100000);
        BigDecimal devilAmount = new BigDecimal(666);
        LoanResponseStatusType loanResponse = LoanResponseStatusType.APPROVED;

        if (loanRequest.getAmount().compareTo(maxAmount) > 0 || loanRequest.getClientType() == ClientType.INC) {
            loanResponse = LoanResponseStatusType.DENIED;
        } else if (loanRequest.getAmount().compareTo(devilAmount) == 0) {
            loanResponse = LoanResponseStatusType.ERROR;
        }

        return loanCalcRepository.save(loanRequest, loanResponse);
    }
}
