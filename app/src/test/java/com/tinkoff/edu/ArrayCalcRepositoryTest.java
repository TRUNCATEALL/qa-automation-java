package com.tinkoff.edu;

import com.tinkoff.edu.app.Utils;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.repository.ArrayCalcRepository;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.app.service.SimpleLoanCalcService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.*;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayCalcRepositoryTest {
    private static LoanCalcController loanCalcController;
    private final static String applicantsName = "Владимир Владимирович Тестировщик";
    ArrayCalcRepository arrayCalcRepository;

    @BeforeEach
    public void prepareRequest() {
        arrayCalcRepository = new ArrayCalcRepository();
        LoanCalcService loanCalcService = new SimpleLoanCalcService(arrayCalcRepository);
        loanCalcController = new LoanCalcController(loanCalcService);
    }

    private LoanRequest createRequestWithRandomParams(ClientType clientType) {
        int amount = Utils.randomInt(0, 10000);
        int month = Utils.randomInt(1, 12);
        return new LoanRequest(applicantsName, month, BigDecimal.valueOf(amount), clientType);
    }

    @Test
    void shouldErrorWhenRepositoryArrayOverflow() {
        for (int i = 0; i < arrayCalcRepository.getRequestsArray().length; i++) {
            LoanRequest loanRequest = new LoanRequest(applicantsName, 11, BigDecimal.valueOf(10001), ClientType.OOO);
            LoanResponse loanResponse = loanCalcController.createRequest(loanRequest);
            assertEquals(APPROVED,
                    loanResponse.getResponseStatus(), "Некорректный статус.");
        }

        LoanRequest loanRequest = new LoanRequest(applicantsName, 14, BigDecimal.valueOf(9999), ClientType.OOO);
        ArrayIndexOutOfBoundsException e = Assertions.assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });
    }

    @Test
    void shouldChangeStatusByUUID() {
        LoanRequest loanRequest = createRequestWithRandomParams(ClientType.OOO);
        LoanResponse loanResponse = loanCalcController.createRequest(loanRequest);

        loanResponse.setStatus(IN_PROGRESS);
        assertEquals(loanResponse.getResponseStatus(), IN_PROGRESS, "Статус не изменился");
    }

    @Test
    void shouldGetResponseStatusByUUID() {
        LoanRequest loanRequest = createRequestWithRandomParams(ClientType.OOO);
        LoanResponse loanResponse = loanCalcController.createRequest(loanRequest);

        assertNotNull(loanResponse.getResponseStatus(), "Не удалось получить статус или статус отсутствует");
    }
}
