package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.repository.SimpleCalcRepository;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.app.service.SimpleLoanCalcService;

import java.math.BigDecimal;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {

    public static void main(String... args) {
        int[] amountValues = {666, 100000, 100001};

        for (int responseId = 0; responseId < 10; responseId++) {

            int amount = Utils.randomValueFromArray(amountValues);
            LoanRequest loanRequestModel = new LoanRequest(12, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));
            LoanCalcService loanCalcService = new SimpleLoanCalcService(new SimpleCalcRepository());
            LoanResponse loanResponse = new LoanCalcController(loanCalcService).createRequest(loanRequestModel);

            System.out.println("--- [ Request params ] ---\n" + loanRequestModel);
            System.out.println("--- [ Response ] ---\n" + loanResponse);
            System.out.println("--- [ Test ] ---");

            if (loanResponse.getRequestId() == responseId) {
                System.out.println("Test passed: requestId = " + loanResponse.getRequestId() + ", expected = " + responseId);

            } else {
                System.out.println("Test failed: requestId = " + loanResponse.getRequestId() + " but expected = " + responseId);
            }
            System.out.println("");
        }
    }
}
