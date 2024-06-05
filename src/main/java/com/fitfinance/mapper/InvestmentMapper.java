package com.fitfinance.mapper;

import com.fitfinance.domain.Finance;
import com.fitfinance.domain.Investment;
import com.fitfinance.request.FinancePostRequest;
import com.fitfinance.request.FinancePutRequest;
import com.fitfinance.request.InvestmentPostRequest;
import com.fitfinance.request.InvestmentPutRequest;
import com.fitfinance.response.FinanceGetResponse;
import com.fitfinance.response.FinancePostResponse;
import com.fitfinance.response.InvestmentGetResponse;
import com.fitfinance.response.InvestmentPostResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestmentMapper {
    Investment toInvestment(InvestmentPostRequest request);

    @Mapping(source = "id", target = "id")
    Investment toInvestment(InvestmentPutRequest request);

    InvestmentPostResponse toInvestmentPostResponse(Investment finance);

    InvestmentGetResponse toInvestmentGetResponse(Investment finance);

    List<InvestmentGetResponse> toInvestmentGetResponses(List<Investment> finances);
}
