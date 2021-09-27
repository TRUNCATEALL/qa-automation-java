package com.tinkoff.edu.app.model;

import com.tinkoff.edu.app.dictionary.LoanResponseStatus;

import java.util.UUID;

/**
 * Loan response
 */
public class LoanResponse {
    private UUID responseId;
    private final UUID requestId;
    private LoanResponseStatus responseStatus;

    public LoanResponse(UUID requestId, LoanResponseStatus responseStatus) {
        this.requestId = requestId;
        this.responseStatus = responseStatus;
        this.responseId = UUID.randomUUID();
    }

    public UUID getResponseId() {
        return responseId;
    }

    public UUID getRequestId() {
        return requestId;
    }

    public LoanResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public String toString() {
        return this.responseId + "," + this.requestId + "," + this.responseStatus;
    }

    public void setStatus(LoanResponseStatus responseStatus){
        this.responseStatus = responseStatus;
    }
}
