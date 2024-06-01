package com.fitfinance.util;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.FinanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FinanceUtils {
    public Finance createValidFinance() {
        return Finance.builder()
                .id(0L)
                .name("Some finance")
                .type(FinanceType.INCOME)
                .value(100)
                .description("Some description")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .build();
    }
}
