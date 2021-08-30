package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.dictionary.LoanResponseStatus;

public interface LoanCalcRepository {
    LoanResponse save(LoanRequest loanRequest, LoanResponseStatus loanResponseStatus);
}
