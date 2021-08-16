package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {

    public static void main(String... args) {
        for (int responseId = 0; responseId < 10; responseId++) {

            int requestId = LoanCalcController.createRequest();
            if (requestId == responseId) {
                System.out.println("Test passed: requestId = " + requestId + ", expected = " + responseId);

            } else {
                System.out.println("Test failed: requestId = " + requestId + " but expected = " + responseId);
            }
        }
    }
}
