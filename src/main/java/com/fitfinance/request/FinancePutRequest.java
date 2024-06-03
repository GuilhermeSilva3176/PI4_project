package com.fitfinance.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fitfinance.domain.FinanceType;
import com.fitfinance.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class FinancePutRequest {
    @NotNull
    private Long id;
    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotBlank(message = "The field 'value' is required")
    private double value;
    @NotBlank(message = "The field 'type' is required")
    private FinanceType type;
    @NotBlank(message = "The field 'description' is required")
    private String description;
    @NotBlank(message = "The field 'startDate' is required")
    private LocalDate startDate;
    @NotBlank(message = "The field 'endDate' is required")
    private LocalDate endDate;
    @NotBlank(message = "The field 'user' is required")
    @JsonIgnore
    private User user;
}
