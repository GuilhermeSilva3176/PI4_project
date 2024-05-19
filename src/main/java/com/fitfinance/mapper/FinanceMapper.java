package com.fitfinance.mapper;

import com.fitfinance.domain.Finance;
import com.fitfinance.request.FinancePostRequest;
import com.fitfinance.request.FinancePutRequest;
import com.fitfinance.response.FinanceGetResponse;
import com.fitfinance.response.FinancePostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE,
uses = PasswordEncoderMapper.class)
public interface FinanceMapper {
    Finance toFinance(FinancePostRequest request);

    Finance toFinance(FinancePutRequest request);

    FinancePostResponse toFinancePostResponse(Finance finance);

    FinanceGetResponse toFinanceGetResponse(Finance finance);
}
