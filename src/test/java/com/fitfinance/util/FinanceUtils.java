package com.fitfinance.util;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.FinanceType;
import com.fitfinance.domain.User;
import com.fitfinance.request.FinancePostRequest;
import com.fitfinance.request.FinancePutRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FinanceUtils {
    public Finance createValidFinance() {
        return Finance.builder()
                .name("Some finance " + System.currentTimeMillis())
                .type(FinanceType.INCOME)
                .value(100)
                .description("Some description " + System.currentTimeMillis())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }

    public Finance createValidFinanceWithUser(User user) {
        return Finance.builder()
                .name("Some finance " + System.currentTimeMillis())
                .type(FinanceType.EXPENSE)
                .value(100)
                .description("Some description " + System.currentTimeMillis())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .user(user)
                .build();
    }

    public FinancePostRequest createValidFinancePostRequest() {
        return FinancePostRequest.builder()
                .name("Some finance " + System.currentTimeMillis())
                .type(FinanceType.EXPENSE)
                .value(100)
                .description("Some description " + System.currentTimeMillis())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }

    public FinancePutRequest createValidFinancePutRequest(Finance finance) {
        return FinancePutRequest.builder()
                .id(finance.getId())
                .name("Updated finance")
                .description(finance.getDescription())
                .value(finance.getValue())
                .type(finance.getType())
                .startDate(finance.getStartDate())
                .endDate(finance.getEndDate())
                .build();
    }
}
