package com.fitfinance.request;

import com.fitfinance.domain.FinanceType;
import com.fitfinance.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class FinancePutRequest {
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
    private User user;
}
