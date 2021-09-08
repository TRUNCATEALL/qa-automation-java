package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.exceptions.BadRequestArguments;
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
        String applicantsName = loanRequest.getFullName();
        int requestMonths = loanRequest.getMonths();

        try {
            validateRequestParams(applicantsName, requestAmount, requestMonths);
        } catch (BadRequestArguments e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        LoanResponseStatus loanResponseStatus = getResponseStatus(loanRequest.getClientType(), cornerAmount, requestAmount, requestMonths);
        LoanResponse loanResponse = new LoanResponse(loanCalcRepository.save(loanRequest), loanRequest, loanResponseStatus);

        return loanResponse;
    }

    private void validateRequestParams(String applicantsName, BigDecimal requestAmount, int requestMonths) throws BadRequestArguments {
        if (requestAmount.compareTo(new BigDecimal(0.01)) < 0 || requestAmount.compareTo(new BigDecimal(999_999.99)) > 0)
            throw new BadRequestArguments("Сумма кредита должна быть не менее 0.01 и не более 999 999.99");

        if (requestMonths <= 0 || requestMonths > 100)
            throw new BadRequestArguments("Срок кредита должен быть не менее 1 и не более 100 месяцев");

        if (applicantsName.length() < 10 || applicantsName.length() > 100)
            throw new BadRequestArguments("ФИО не должно быть короче 10 и длиннее 100 символов");

        if (!applicantsName.matches("[а-яА-Я-]+"))
            throw new BadRequestArguments("ФИО должно содержать только буквы и -");
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
