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
    private LoanRequest loanRequestModel;
    private LoanCalcService loanCalcService;
    private LoanCalcRepository loanCalcRepository;
    private LoanCalcController loanCalcController;
    private int months;
    private int amount;

    @BeforeEach
    public void generateRequest() {
        int[] amountValues = {666, 100000, 100001};
        amount = Utils.randomValueFromArray(amountValues);
        months = Utils.randomInt(1, 12);
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        loanCalcRepository = new SimpleCalcRepository();
        loanCalcService = new SimpleLoanCalcService(loanCalcRepository);
        loanRequestModel = new LoanRequest(months, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));
        loanCalcController = new LoanCalcController(loanCalcService);

        int requestId = loanCalcController.createRequest(loanRequestModel).getRequestId();

        Assertions.assertEquals(1, requestId);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        loanCalcRepository = new SimpleCalcRepository();
        loanCalcService = new SimpleLoanCalcService(loanCalcRepository);
        loanRequestModel = new LoanRequest(months, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));
        loanCalcController = new LoanCalcController(loanCalcService);

        int currentRequestId = loanCalcController.createRequest(loanRequestModel).getRequestId();
        int actualRequestId = loanCalcController.createRequest(loanRequestModel).getRequestId();

        Assertions.assertEquals(currentRequestId + 1, actualRequestId);
    }
}
