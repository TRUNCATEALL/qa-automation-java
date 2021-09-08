package com.tinkoff.edu;

import com.tinkoff.edu.app.Utils;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.repository.LoanCalcRepository;
import com.tinkoff.edu.app.repository.SimpleCalcRepository;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.app.service.SimpleLoanCalcService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.APPROVED;
import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.DECLINED;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Loan Calc Tests
 */
public class LoanCalcTest {
    private static LoanCalcController loanCalcController;
    private final static String applicantsName = "Владимир-Владимирович-Тестировщик";

    @BeforeAll
    public static void generateRequest() {
        LoanCalcRepository loanCalcRepository = new SimpleCalcRepository();
        LoanCalcService loanCalcService = new SimpleLoanCalcService(loanCalcRepository);
        loanCalcController = new LoanCalcController(loanCalcService);
    }

    private void assertApproved(LoanResponseStatus status) {
        assertEquals(APPROVED, status, "Заявка должна быть одобрена");
    }

    private void assertDeclied(LoanResponseStatus status) {
        assertEquals(DECLINED, status, "Заявка не должна быть одобрена");
    }

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        NullPointerException e = Assertions.assertThrows(NullPointerException.class, () -> {
            loanCalcController.createRequest(null);
        });
        assertEquals(e.getMessage(), "Данные по заявке отсутствуют");
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequest() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 12, BigDecimal.valueOf(0), Utils.randomEnum(ClientType.class));

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });
        assertEquals(e.getMessage(), "Сумма кредита должна быть не менее 0.01 и не более 999 999.99");
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNagativeMonthsRequest() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 0, BigDecimal.valueOf(10000), Utils.randomEnum(ClientType.class));

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });
        assertEquals(e.getMessage(), "Срок кредита должен быть не менее 1 и не более 100 месяцев");
    }

    @Test
    public void shouldGetDeclineWhenClientPerson() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 13, BigDecimal.valueOf(10001), ClientType.PERSON);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(loanResponseStatus);
    }

    @Test
    public void shouldGetDeclineWhenClientPersonAndMonthsLowerThenMax() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 11, BigDecimal.valueOf(10001), ClientType.PERSON);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(loanResponseStatus);
    }

    @Test
    public void shouldGetDeclineWhenClientPersonAndAmountLowerThenMax() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 13, BigDecimal.valueOf(9999), ClientType.PERSON);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(loanResponseStatus);
    }

    @Test
    public void shouldGetApproveWhenClientPerson() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 11, BigDecimal.valueOf(9999), ClientType.PERSON);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertApproved(loanResponseStatus);
    }

    @Test
    public void shouldGetDeclineWhenClientOooAndAmountMoreThenMax() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 14, BigDecimal.valueOf(10001), ClientType.OOO);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(loanResponseStatus);
    }

    @Test
    public void shouldGetApproveWhenClientOooAndAmountMoreThenMax() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 11, BigDecimal.valueOf(10001), ClientType.OOO);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertApproved(loanResponseStatus);
    }

    @Test
    public void shouldGetDeclineWhenClientOooAndAmountLowerThenMax() {
        LoanRequest loanRequest = new LoanRequest(applicantsName, 14, BigDecimal.valueOf(9999), ClientType.OOO);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(loanResponseStatus);
    }

    @Test
    public void shouldGetDeclineWhenClientIp() {
        int amount = Utils.randomInt(0, 10000);
        int months = Utils.randomInt(1, 12);
        LoanRequest loanRequest = new LoanRequest(applicantsName, months, BigDecimal.valueOf(amount), ClientType.IP);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(loanResponseStatus);
    }

    @Test
    public void shouldGetErrorWhenUnknownClientType() {
        int amount = Utils.randomInt(0, 10000);
        int months = Utils.randomInt(1, 12);
        LoanRequest loanRequest = new LoanRequest(applicantsName, months, BigDecimal.valueOf(amount), ClientType.OAO);

        NullPointerException e = Assertions.assertThrows(NullPointerException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });
        assertEquals("Неизвестный тип клиента", e.getMessage());
    }
}
