package com.tinkoff.edu;

import com.tinkoff.edu.app.Utils;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.repository.SimpleCalcRepository;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.app.service.SimpleLoanCalcService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    private LoanCalcController loanCalcController;
    private int months;
    private int amount;

    @BeforeEach
    public void generateRequest() {
        int[] amountValues = {666, 100000, 100001};
        amount = Utils.randomValueFromArray(amountValues);
        months = Utils.randomInt(1, 12);

        LoanCalcRepository loanCalcRepository = new SimpleCalcRepository();
        LoanCalcService loanCalcService = new SimpleLoanCalcService(loanCalcRepository);
        loanCalcController = new LoanCalcController(loanCalcService);
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        LoanRequest loanRequestModel = new LoanRequest(months, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));

        int requestId = loanCalcController.createRequest(loanRequestModel).getRequestId();

        Assertions.assertEquals(1, requestId);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        LoanRequest loanRequestModel = new LoanRequest(months, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));

        int currentRequestId = loanCalcController.createRequest(loanRequestModel).getRequestId();
        int actualRequestId = loanCalcController.createRequest(loanRequestModel).getRequestId();

        Assertions.assertEquals(currentRequestId + 1, actualRequestId);
    }
}
