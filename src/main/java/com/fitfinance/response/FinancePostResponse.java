package com.fitfinance.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitfinance.domain.FinanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class FinancePostResponse {
    @Schema(description = "Finance's ID", example = "1")
    private Long id;
    @Schema(description = "Finance's name", example = "Salary")
    private String name;
    @Schema(description = "Finance's description", example = "Monthly salary")
    private String description;
    @Schema(description = "Finance's value", example = "1000.00")
    private double value;
    @Schema(description = "Finance's type", example = "INCOME")
    private FinanceType type;
    @Schema(description = "Finance's start date", example = "2021-01-01")
    @JsonProperty("start_date")
    private LocalDate startDate;
    @Schema(description = "Finance's end date", example = "2021-01-31")
    @JsonProperty("end_date")
    private LocalDate endDate;
    @Schema(description = "Finance's user", example = "User")
    @JsonIgnore
    private UserPostResponse user;
}
