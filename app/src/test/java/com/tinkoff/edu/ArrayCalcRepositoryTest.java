package com.tinkoff.edu;

import com.tinkoff.edu.app.Utils;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.repository.ArrayCalcRepository;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.app.service.SimpleLoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.IN_PROGRESS;
import static org.junit.jupiter.api.Assertions.*;

public class ArrayCalcRepositoryTest {
    private static LoanCalcController loanCalcController;
    private final static String applicantsName = "Владимир-Владимирович-Тестировщик";
    ArrayCalcRepository arrayCalcRepository;

    @BeforeEach
    public void prepare() {
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
        for (int i = 0; i < arrayCalcRepository.getMaxCapacity(); i++) {
            LoanRequest loanRequest = new LoanRequest(applicantsName, 11, BigDecimal.valueOf(10001), ClientType.OOO);
            loanCalcController.createRequest(loanRequest);
        }

        LoanRequest loanRequest = new LoanRequest(applicantsName, 14, BigDecimal.valueOf(9999), ClientType.OOO);
        assertThrows(IllegalStateException.class, () -> {
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

    @Test
    void shouldErrorWhenSendRequestWithBadName() {
        LoanRequest loanRequest = new LoanRequest("BadName-12345", 14, BigDecimal.valueOf(9999), ClientType.OOO);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });

        assertEquals("ФИО должно содержать только буквы и -", e.getMessage());
    }

    @Test
    void shouldErrorWhenSendRequestWithBadLengthName() {
        String overloadName = Utils.generateStringFromAlphabetWithLength(101, "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
        Collection<String> badNames = List.of("BadName", overloadName);

        for (String badName : badNames) {
            LoanRequest loanRequest = new LoanRequest(badName, 14, BigDecimal.valueOf(9999), ClientType.OOO);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                loanCalcController.createRequest(loanRequest);
            });

            assertEquals("ФИО не должно быть короче 10 и длиннее 100 символов", e.getMessage());
        }
    }

    @Test
    void shouldErrorWhenSendRequestWithBadTerms() {
        Collection<Integer> badTerms = List.of(0, 101);

        for (Integer badTerm : badTerms) {
            LoanRequest loanRequest = new LoanRequest(applicantsName, badTerm, BigDecimal.valueOf(9999), ClientType.OOO);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                loanCalcController.createRequest(loanRequest);
            });

            assertEquals("Срок кредита должен быть не менее 1 и не более 100 месяцев", e.getMessage());
        }
    }

    @Test
    void shouldErrorWhenSendRequestWithBadAmount() {
        Collection<BigDecimal> badAmounts = List.of(new BigDecimal(0.001), new BigDecimal(999_999.99).add(BigDecimal.ONE));

        for (BigDecimal badAmount : badAmounts) {
            LoanRequest loanRequest = new LoanRequest(applicantsName, 14, badAmount, ClientType.OOO);
            IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
                loanCalcController.createRequest(loanRequest);
            });

            assertEquals("Сумма кредита должна быть не менее 0.01 и не более 999 999.99", e.getMessage());
        }
    }

    @Test
    void shouldErrorInRepoWhenRequestIsNull() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> {
            arrayCalcRepository.save(null);
        });

        assertEquals("Данные по заявке отсутствуют", e.getMessage());
    }

    @Test
    void shouldErrorInControllerWhenRequestIsNull() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> {
            loanCalcController.createRequest(null);
        });

        assertEquals("Данные по заявке отсутствуют", e.getMessage());
    }

    @Test
    void shouldErrorInServiceWhenRequestIsNull() {
        LoanCalcService loanCalcService = new SimpleLoanCalcService(arrayCalcRepository);

        NullPointerException e = assertThrows(NullPointerException.class, () -> {
            loanCalcService.createRequest(null);
        });

        assertEquals("Данные по заявке отсутствуют", e.getMessage());
    }
}
