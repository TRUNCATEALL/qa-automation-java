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

import static com.tinkoff.edu.app.repository.SimpleCalcRepository.getRequestId;
import static com.tinkoff.edu.app.repository.SimpleCalcRepository.setRequestId;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    private LoanRequest loanRequestModel;
    private LoanCalcService loanCalcService;

    @BeforeEach
    public void generateRequest() {
        int[] amountValues = {666, 100000, 100001};
        int amount = Utils.randomValueFromArray(amountValues);
        int months = Utils.randomInt(1, 12);
        LoanCalcRepository loanCalcRepository = new SimpleCalcRepository();
        loanCalcService = new SimpleLoanCalcService(loanCalcRepository);
        loanRequestModel = new LoanRequest(months, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));
    }

    @Test
    public void shouldGetId1WhenFirstCall() {
        setRequestId(0);
        int requestId = new LoanCalcController(loanCalcService).createRequest(loanRequestModel).getRequestId();

        Assertions.assertEquals(1, requestId);
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        int currentRequestId = getRequestId();
        int actualRequestId = new LoanCalcController(loanCalcService).createRequest(loanRequestModel).getRequestId();

        Assertions.assertEquals(currentRequestId + 1, actualRequestId);
    }
}
