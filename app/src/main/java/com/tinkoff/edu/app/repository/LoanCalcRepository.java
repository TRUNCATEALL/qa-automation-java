package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

public interface LoanCalcRepository {

    boolean save(LoanRequest loanRequest, LoanResponse loanResponse);
}
