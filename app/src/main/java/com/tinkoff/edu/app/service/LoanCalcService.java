package com.tinkoff.edu.app.service;

import com.tinkoff.edu.app.dictionary.ClientType;
import com.tinkoff.edu.app.dictionary.LoanResponseStatus;
import com.tinkoff.edu.app.model.LoanRequest;
import com.tinkoff.edu.app.model.LoanResponse;

import java.math.BigDecimal;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest loanRequest);

    default LoanResponseStatus getResponseStatus(ClientType clientType, BigDecimal cornerAmount, BigDecimal amount, int months) {
        switch (clientType) {
            case PERSON:
                return getReqStatusForPerson(cornerAmount, amount, months);
            case OOO:
                return getReqStatusForOoo(cornerAmount, amount, months);
            case IP:
                return getReqStatusForIp();
            default:
                throw new NullPointerException("Неизвестный тип клиента");
        }
    }

    default LoanResponseStatus getReqStatusForPerson(BigDecimal cornerAmount, BigDecimal amount, int months) {
        LoanResponseStatus loanResponseStatus = LoanResponseStatus.DECLINED;

        if (amount.compareTo(cornerAmount) <= 0 && months <= 12) {
            loanResponseStatus = LoanResponseStatus.APPROVED;
        }
        return loanResponseStatus;
    }

    default LoanResponseStatus getReqStatusForOoo(BigDecimal cornerAmount, BigDecimal amount, int months) {
        LoanResponseStatus loanResponseStatus = LoanResponseStatus.DECLINED;

        if (amount.compareTo(cornerAmount) > 0 && months <= 12) {
            loanResponseStatus = LoanResponseStatus.APPROVED;
        }
        return loanResponseStatus;
    }

    default LoanResponseStatus getReqStatusForIp() {
        return LoanResponseStatus.DECLINED;
    }
}
