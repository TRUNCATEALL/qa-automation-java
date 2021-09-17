package com.tinkoff.edu;

import com.tinkoff.edu.app.Utils;
import com.tinkoff.edu.app.controller.LoanCalcController;
import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.repository.HashMapLoanCalcRepository;
import com.tinkoff.edu.app.service.LoanCalcService;
import com.tinkoff.edu.app.service.SimpleLoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Stream;

import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.APPROVED;
import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.DECLINED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashMapCalcRepositoryTests {
    private final static String applicantsName = "Владимир-Владимирович-Тестировщик";
    private static LoanCalcController loanCalcController;
    HashMapLoanCalcRepository hashMapLoanCalcRepository;

    @BeforeEach
    public void prepare() {
        hashMapLoanCalcRepository = new HashMapLoanCalcRepository();
        LoanCalcService loanCalcService = new SimpleLoanCalcService(hashMapLoanCalcRepository);
        loanCalcController = new LoanCalcController(loanCalcService);
    }

    @ParameterizedTest
    @MethodSource("getPersonsLoanParametersForPositiveResponse")
    void shouldGetApproveWhenClientPerson(BigDecimal amount, int term) {
        LoanRequest loanRequest = new LoanRequest(applicantsName, term, amount, ClientType.PERSON);

        LoanResponseStatus actualStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertApproved(actualStatus);
    }

    private static Stream<Arguments> getPersonsLoanParametersForPositiveResponse() {
        return Stream.of(Arguments.arguments(BigDecimal.valueOf(9999), 11),
                Arguments.arguments(BigDecimal.valueOf(10000), 12));
    }

    @ParameterizedTest
    @MethodSource("getPersonsLoanParametersForNegativeResponse")
    void shouldGetDeclineWhenClientPerson(BigDecimal amount, int term) {
        LoanRequest loanRequest = new LoanRequest(applicantsName, term, amount, ClientType.PERSON);

        LoanResponseStatus actualStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(actualStatus);
    }

    private static Stream<Arguments> getPersonsLoanParametersForNegativeResponse() {
        return Stream.of(Arguments.arguments(BigDecimal.valueOf(999999.99), 100),
                Arguments.arguments(BigDecimal.valueOf(10001), 13),
                Arguments.arguments(BigDecimal.valueOf(10001), 11),
                Arguments.arguments(BigDecimal.valueOf(9999), 13));
    }

    @ParameterizedTest
    @MethodSource("getOooLoanParametersForPositiveResponse")
    void shouldGetApproveWhenClientOoo(BigDecimal amount, int term) {
        LoanRequest loanRequest = new LoanRequest(applicantsName, term, amount, ClientType.OOO);

        LoanResponseStatus actualStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertApproved(actualStatus);
    }

    private static Stream<Arguments> getOooLoanParametersForPositiveResponse() {
        return Stream.of(Arguments.arguments(BigDecimal.valueOf(10001), 12),
                Arguments.arguments(BigDecimal.valueOf(99999), 1));
    }

    @ParameterizedTest
    @MethodSource("getOooLoanParametersForNegativeResponse")
    void shouldGetDeclineWhenClientOoo(BigDecimal amount, int term) {
        LoanRequest loanRequest = new LoanRequest(applicantsName, term, amount, ClientType.OOO);

        LoanResponseStatus actualStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(actualStatus);
    }

    private static Stream<Arguments> getOooLoanParametersForNegativeResponse() {
        return Stream.of(Arguments.arguments(BigDecimal.valueOf(10001), 13),
                Arguments.arguments(BigDecimal.valueOf(9999), 12),
                Arguments.arguments(BigDecimal.valueOf(9999), 1));
    }

    @ParameterizedTest
    @MethodSource("getIpLoanParametersForNegativeResponse")
    void shouldGetDeclineWhenClientIp(BigDecimal amount, int term) {
        LoanRequest loanRequest = new LoanRequest(applicantsName, term, amount, ClientType.IP);

        LoanResponseStatus actualStatus = loanCalcController.createRequest(loanRequest).getResponseStatus();

        assertDeclied(actualStatus);
    }

    private static Stream<Arguments> getIpLoanParametersForNegativeResponse() {
        int amount = Utils.randomInt(0, 10000);
        int months = Utils.randomInt(1, 12);

        return Stream.of(Arguments.arguments(BigDecimal.valueOf(10000), 12),
                Arguments.arguments(BigDecimal.valueOf(9999), 11),
                Arguments.arguments(BigDecimal.valueOf(10001), 13),
                Arguments.arguments(BigDecimal.valueOf(amount), months));
    }

    @Test
    void shouldGetAllOooRequests() {
        Map<UUID, LoanRequest> oooMap = new HashMap<>();

        for (int i = 0; i < 3; i++) {
            LoanRequest loanRequest = createRequestWithRandomParams(ClientType.PERSON);
            loanCalcController.createRequest(loanRequest);
        }

        for (int i = 0; i < 2; i++) {
            LoanRequest loanRequest = createRequestWithRandomParams(ClientType.OOO);
            loanCalcController.createRequest(loanRequest);
            oooMap.put(loanRequest.getRequestId(), loanRequest);
        }

        assertThat(hashMapLoanCalcRepository.getRequests())
                .isNotEmpty()
                .containsAllEntriesOf(oooMap);
    }

    @Test
    void shouldThrowErrorInRepoWhenRequestIsNull() {
        NullPointerException e = assertThrows(NullPointerException.class, () -> {
            hashMapLoanCalcRepository.save(null, null);
        });

        assertEquals("Данные по заявке отсутствуют", e.getMessage());
    }

    private LoanRequest createRequestWithRandomParams(ClientType clientType) {
        int amount = Utils.randomInt(0, 10000);
        int month = Utils.randomInt(1, 12);
        return new LoanRequest(applicantsName, month, BigDecimal.valueOf(amount), clientType);
    }

    private void assertApproved(LoanResponseStatus status) {
        assertEquals(APPROVED, status, "Заявка должна быть одобрена");
    }

    private void assertDeclied(LoanResponseStatus status) {
        assertEquals(DECLINED, status, "Заявка не должна быть одобрена");
    }
}
