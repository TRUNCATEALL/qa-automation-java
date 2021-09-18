package com.tinkoff.edu.app.model;

import com.tinkoff.edu.app.dictionary.LoanResponseStatus;

import java.util.UUID;

/**
 * Loan response
 */
public class LoanResponse {
    private UUID responseId;
    private final LoanRequest loanRequest;
    private LoanResponseStatus responseStatus;

    public LoanResponse(UUID responseId, LoanRequest loanRequest, LoanResponseStatus responseStatus) {
        this.responseId = responseId;
        this.loanRequest = loanRequest;
        this.responseStatus = responseStatus;
        this.responseId = UUID.randomUUID();
    }

    public UUID getResponseId() {
        return responseId;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public LoanResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String toString() {
        return "requestId: " + this.responseId + "\nrequest: " + this.loanRequest + "\nresponseStatus: " + this.responseStatus;
    }

    public void setStatus(LoanResponseStatus responseStatus){
        this.responseStatus = responseStatus;
    }
}
