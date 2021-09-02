package com.tinkoff.edu.app.model;

import com.tinkoff.edu.app.dictionary.LoanResponseStatus;

import java.util.UUID;

/**
 * Loan response
 */
public class LoanResponse {
    private final UUID requestId;
    private final LoanRequest loanRequest;
    private LoanResponseStatus responseStatus;

    public LoanResponse(UUID requestId, LoanRequest loanRequest, LoanResponseStatus responseStatus) {
        this.requestId = requestId;
        this.loanRequest = loanRequest;
        this.responseStatus = responseStatus;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public LoanResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String toString() {
        return "requestId: " + this.requestId + "\nrequest: " + this.loanRequest + "\nresponseStatus: " + this.responseStatus;
    }

    public void setStatus(LoanResponseStatus responseStatus){
        this.responseStatus = responseStatus;
    }
}
