package com.fitfinance.mapper;

import com.fitfinance.domain.Investment;
import com.fitfinance.request.InvestmentPostRequest;
import com.fitfinance.request.InvestmentPutRequest;
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

    InvestmentPostResponse toInvestmentPostResponse(Investment investment);

    InvestmentGetResponse toInvestmentGetResponse(Investment investment);

    List<InvestmentGetResponse> toInvestmentGetResponses(List<Investment> investment);
}