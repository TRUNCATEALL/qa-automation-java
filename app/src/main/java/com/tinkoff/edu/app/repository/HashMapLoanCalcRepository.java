package com.tinkoff.edu.app.repository;

import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HashMapLoanCalcRepository implements LoanCalcRepository {

    private final Map<UUID, LoanRequest> requestsMap = new HashMap<>();;
    private final Map<UUID, LoanResponse> responsesMap = new HashMap<>();;

    public Map<UUID, LoanRequest> getRequests() {
        return Collections.unmodifiableMap(requestsMap);
    }

    public Map<UUID, LoanResponse> getResponses() {
        return Collections.unmodifiableMap(responsesMap);
    }

    @Override
    public boolean save(LoanRequest loanRequest, LoanResponse loanResponse) {

        if (loanRequest == null)
            throw new NullPointerException("Данные по заявке отсутствуют");

        try {
            requestsMap.put(loanRequest.getRequestId(), loanRequest);
            responsesMap.put(loanResponse.getResponseId(), loanResponse);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
