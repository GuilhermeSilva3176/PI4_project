package com.fitfinance.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitfinance.domain.InvestmentType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class InvestmentPostResponse {
    @Schema(description = "Investment's ID", example = "1")
    private Long id;
    @Schema(description = "Investment's name", example = "Salary")
    private String name;
    @Schema(description = "Investment's quantity", example = "1")
    private Integer quantity;
    @Schema(description = "Investment's price", example = "1000.00")
    private Double price;
    @Schema(description = "Investment's type", example = "STOCKS")
    private InvestmentType type;
    @Schema(description = "Investment's start date", example = "2021-01-01")
    @JsonProperty("start_date")
    private LocalDate startDate;
    @Schema(description = "Investment's end date", example = "2021-01-31")
    @JsonProperty("end_date")
    private LocalDate endDate;
    @Schema(description = "Investment's user", example = "User")
    @JsonIgnore
    private UserPostResponse user;
}
