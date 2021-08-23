package com.tinkoff.edu.app.model;

import com.tinkoff.edu.app.dictionary.LoanResponseStatusType;

import static com.tinkoff.edu.app.Utils.requestCounter;

/**
 * Loan response
 */
public class LoanResponse {
    private final int requestId;
    private final LoanRequest loanRequest;
    private final LoanResponseStatusType responseStatus;

    public LoanResponse(LoanRequest loanRequest, LoanResponseStatusType responseStatus) {
        this.requestId = requestCounter();
        this.loanRequest = loanRequest;
        this.responseStatus = responseStatus;
    }

    public int getRequestId() {
        return requestId;
    }

    public LoanRequest getLoanRequest() {
        return loanRequest;
    }

    public LoanResponseStatusType getResponseStatus() {
        return responseStatus;
    }

    public String toString() {
        return "requestId: " + this.requestId + "\nrequest: " + this.loanRequest + "\nresponseStatus: " + this.responseStatus;
    }
}