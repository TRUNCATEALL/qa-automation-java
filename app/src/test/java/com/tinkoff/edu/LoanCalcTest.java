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
import org.junit.jupiter.api.Disabled;
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

    private LoanRequest createRequestWithRandomParams() {
        int amount = Utils.randomInt(0, 10000);
        int month = Utils.randomInt(1, 12);
        return new LoanRequest(month, BigDecimal.valueOf(amount), Utils.randomEnum(ClientType.class));
    }

    @Test
    @Disabled
    public void shouldGetId1WhenFirstCall() {
        generateRequest();
        LoanRequest loanRequest = createRequestWithRandomParams();

        int requestId = loanCalcController.createRequest(loanRequest).getRequestId();

        Assertions.assertEquals(1, requestId);
    }

    @Test
    @Disabled
    public void shouldGetIncrementedIdWhenAnyCall() {
        LoanRequest loanRequest = createRequestWithRandomParams();

        int currentRequestId = loanCalcController.createRequest(loanRequest).getRequestId();
        int actualRequestId = loanCalcController.createRequest(loanRequest).getRequestId();

        Assertions.assertEquals(currentRequestId + 1, actualRequestId);
    }

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        NullPointerException e = Assertions.assertThrows(NullPointerException.class, () -> {
            loanCalcController.createRequest(null);
        });
        Assertions.assertEquals(e.getMessage(), "Данные по заявке отсутствуют");
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequest() {
        LoanRequest loanRequest = new LoanRequest(12, BigDecimal.valueOf(0), Utils.randomEnum(ClientType.class));

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });
        Assertions.assertEquals(e.getMessage(), "Сумма кредита должна быть больше 0");
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNagativeMonthsRequest() {
        LoanRequest loanRequest = new LoanRequest(0, BigDecimal.valueOf(10000), Utils.randomEnum(ClientType.class));

        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });
        Assertions.assertEquals(e.getMessage(), "Срок кредита должен быть больше 0");
    }

    @Test
    public void shouldGetDeclineWhenClientPerson() {
        LoanRequest loanRequest = new LoanRequest(13, BigDecimal.valueOf(10001), ClientType.PERSON);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        Assertions.assertEquals(loanResponseStatus, LoanResponseStatus.DECLINED, "Заявка не должна быть одобрена");
    }

    @Test
    public void shouldGetApproveWhenClientPerson() {
        LoanRequest loanRequest = new LoanRequest(11, BigDecimal.valueOf(9999), ClientType.PERSON);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        Assertions.assertEquals(loanResponseStatus, LoanResponseStatus.APPROVED, "Заявка должна быть одобрена");
    }

    @Test
    public void shouldGetApproveWhenClientPersonAndAmountMoreThenMax() {
        LoanRequest loanRequest = new LoanRequest(11, BigDecimal.valueOf(10001), ClientType.PERSON);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        Assertions.assertEquals(loanResponseStatus, LoanResponseStatus.APPROVED, "Заявка должна быть одобрена");
    }

    @Test
    public void shouldGetDeclineWhenClientOooAndAmountMoreThenMax() {
        LoanRequest loanRequest = new LoanRequest(14, BigDecimal.valueOf(10001), ClientType.OOO);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        Assertions.assertEquals(loanResponseStatus, LoanResponseStatus.DECLINED, "Заявка не должна быть одобрена");
    }

    @Test
    public void shouldGetApproveWhenClientOooAndAmountMoreThenMax() {
        LoanRequest loanRequest = new LoanRequest(11, BigDecimal.valueOf(10001), ClientType.OOO);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        Assertions.assertEquals(loanResponseStatus, LoanResponseStatus.APPROVED, "Заявка должна быть одобрена");
    }

    @Test
    public void shouldGetDeclineWhenClientOooAndAmountLowerThenMax() {
        LoanRequest loanRequest = new LoanRequest(14, BigDecimal.valueOf(9999), ClientType.OOO);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        Assertions.assertEquals(loanResponseStatus, LoanResponseStatus.DECLINED, "Заявка не должна быть одобрена");
    }

    @Test
    public void shouldGetDeclineWhenClientIp() {
        int amount = Utils.randomInt(0, 10000);
        int months = Utils.randomInt(1, 12);
        LoanRequest loanRequest = new LoanRequest(months, BigDecimal.valueOf(amount), ClientType.IP);
        LoanResponseStatus loanResponseStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        Assertions.assertEquals(loanResponseStatus, LoanResponseStatus.DECLINED, "Заявка не должна быть одобрена");
    }

    @Test
    public void shouldGetErrorWhenUnknownClientType() {
        int amount = Utils.randomInt(0, 10000);
        int months = Utils.randomInt(1, 12);
        LoanRequest loanRequest = new LoanRequest(months, BigDecimal.valueOf(amount), ClientType.OAO);

        NullPointerException e = Assertions.assertThrows(NullPointerException.class, () -> {
            loanCalcController.createRequest(loanRequest);
        });
        Assertions.assertEquals(e.getMessage(), "Неизвестный тип клиента");
    }
}
