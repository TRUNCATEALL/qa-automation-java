package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

import java.math.BigDecimal;

import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.APPROVED;
import static com.tinkoff.edu.app.dictionary.LoanResponseStatus.DECLINED;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest loanRequest);

    default LoanResponseStatus getResponseStatus(ClientType clientType, BigDecimal cornerAmount, BigDecimal amount, int months) {
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

    default LoanResponseStatus getRespStatusForPerson(BigDecimal cornerAmount, BigDecimal amount, int months) {

        if (amount.compareTo(cornerAmount) <= 0 && months <= 12) {
            return APPROVED;
        } else {
            return DECLINED;
        }
    }

    default LoanResponseStatus getRespStatusForOoo(BigDecimal cornerAmount, BigDecimal amount, int months) {

        if (amount.compareTo(cornerAmount) > 0 && months <= 12) {
            return APPROVED;
        } else {
            return DECLINED;
        }
    }

    default LoanResponseStatus getRespStatusForIp() {
        return DECLINED;
    }
}
