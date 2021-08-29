package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.dictionary.LoanResponseStatusType;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;
import com.tinkoff.edu.app.repository.LoanCalcRepository;

import java.math.BigDecimal;

/**
 * Loan calculation
 */
public class SimpleLoanCalcService implements LoanCalcService {
    private final LoanCalcRepository loanCalcRepository;

    public SimpleLoanCalcService(LoanCalcRepository loanCalcRepository) {
        this.loanCalcRepository = loanCalcRepository;
    }

    public LoanResponse createRequest(LoanRequest loanRequest) {
        if (loanRequest == null) throw new NullPointerException("Данные по заявке отсутствуют");

        BigDecimal cornerAmount = new BigDecimal(10000);
        BigDecimal requestAmount = loanRequest.getAmount();
        int requestMonths = loanRequest.getMonths();

        if (requestAmount.compareTo(new BigDecimal(0)) <= 0) {
            throw new IllegalArgumentException("Сумма кредита должна быть больше 0");
        }

        if (requestMonths <= 0) {
            throw new IllegalArgumentException("Срок кредита должен быть больше 0");
        }

        LoanResponseStatusType loanResponse = LoanResponseStatusType.APPROVED;
        switch (loanRequest.getClientType()) {
            case PERSON:
                if (requestAmount.compareTo(cornerAmount) > 0 && requestMonths > 12) {
                    loanResponse = LoanResponseStatusType.DECLINED;
                }
                break;
            case OOO:
                if (requestAmount.compareTo(cornerAmount) > 0) {
                    if (requestMonths >= 12) loanResponse = LoanResponseStatusType.DECLINED;
                } else {
                    loanResponse = LoanResponseStatusType.DECLINED;
                }
                break;
            case IP:
                loanResponse = LoanResponseStatusType.DECLINED;
                break;
            default:
                throw new NullPointerException("Неизвестный тип клиента");
        }

        return loanCalcRepository.save(loanRequest, loanResponse);
    }
}
