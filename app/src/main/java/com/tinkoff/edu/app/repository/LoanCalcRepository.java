package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.dictionary.LoanResponseStatusType;

public interface LoanCalcRepository {
    LoanResponse save(LoanRequest loanRequest, LoanResponseStatusType loanResponseStatusType);
}
