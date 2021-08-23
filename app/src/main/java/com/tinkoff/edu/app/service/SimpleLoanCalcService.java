package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.dictionary.LoanResponseStatusType;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

import java.math.BigDecimal;

/**
 * Loan calculation
 */
public class SimpleLoanCalcService implements LoanCalcService {
    private final LoanCalcRepository loanCalcRepository;

    public SimpleLoanCalcService(LoanCalcRepository loanCalcRepository) {
        this.loanCalcRepository = loanCalcRepository;
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
