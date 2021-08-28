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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    private static LoanCalcController loanCalcController;

    @BeforeAll
    public static void generateRequest() {
        LoanCalcRepository loanCalcRepository = new SimpleCalcRepository();
        LoanCalcService loanCalcService = new SimpleLoanCalcService(loanCalcRepository);
        loanCalcController = new LoanCalcController(loanCalcService);
    }

    private LoanRequest createRequest() {
        int amount = Utils.randomInt(0, 10000);
        int month = Utils.randomInt(1, 12);
        return new LoanRequest(month, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        generateRequest();
        LoanRequest loanRequest = createRequest();

        int requestId = loanCalcController.createRequest(loanRequest).getRequestId();

        Assertions.assertEquals(1, requestId);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        LoanRequest loanRequest = createRequest();

        int currentRequestId = loanCalcController.createRequest(loanRequest).getRequestId();
        int actualRequestId = loanCalcController.createRequest(loanRequest).getRequestId();

        Assertions.assertEquals(currentRequestId + 1, actualRequestId);
    }
}
