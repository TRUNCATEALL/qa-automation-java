package com.tinkoff.edu.app;

/**
 * Loan calculation
 */
public class LoanCalcService {

    public static int createRequest() {
        return LoanCalcRepository.save();
    }
}
