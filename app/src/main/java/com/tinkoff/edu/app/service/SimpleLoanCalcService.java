package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

import java.math.BigDecimal;

import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.APPROVED;
import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.DECLINED;

/**
 * Loan calculation
 */
public class SimpleLoanCalcService implements LoanCalcService {
    private final LoanCalcRepository loanCalcRepository;

    public SimpleLoanCalcService(LoanCalcRepository loanCalcRepository) {
        this.loanCalcRepository = loanCalcRepository;
    }

    public LoanResponse createRequest(LoanRequest loanRequest) {
        if (loanRequest == null)
            throw new NullPointerException("Данные по заявке отсутствуют");

        BigDecimal cornerAmount = new BigDecimal(10000);
        BigDecimal requestAmount = loanRequest.getAmount();
        int requestMonths = loanRequest.getMonths();

        if (requestAmount.compareTo(new BigDecimal(0)) <= 0)
            throw new IllegalArgumentException("Сумма кредита должна быть больше 0");

        if (requestMonths <= 0)
            throw new IllegalArgumentException("Срок кредита должен быть больше 0");

        LoanResponseStatus loanResponseStatus = getResponseStatus(loanRequest.getClientType(), cornerAmount, requestAmount, requestMonths);
        LoanResponse loanResponse = new LoanResponse(loanCalcRepository.save(loanRequest), loanRequest, loanResponseStatus);

        return loanResponse;
    }

    private LoanResponseStatus getResponseStatus(ClientType clientType, BigDecimal cornerAmount, BigDecimal amount, int months) {
        switch (clientType) {
            case PERSON:
                return getRespStatusForPerson(cornerAmount, amount, months);
            case OOO:
                return getRespStatusForOoo(cornerAmount, amount, months);
            case IP:
                return getRespStatusForIp();
            default:
                throw new NullPointerException("Неизвестный тип клиента");
        }
    }

    private LoanResponseStatus getRespStatusForPerson(BigDecimal cornerAmount, BigDecimal amount, int months) {

        if (amount.compareTo(cornerAmount) <= 0 && months <= 12) {
            return APPROVED;
        } else {
            return DECLINED;
        }
    }

    private LoanResponseStatus getRespStatusForOoo(BigDecimal cornerAmount, BigDecimal amount, int months) {

        if (amount.compareTo(cornerAmount) > 0 && months <= 12) {
            return APPROVED;
        } else {
            return DECLINED;
        }
    }

    private LoanResponseStatus getRespStatusForIp() {
        return DECLINED;
    }
}
