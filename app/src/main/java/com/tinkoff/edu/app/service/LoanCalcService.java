package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest loanRequest);
}
