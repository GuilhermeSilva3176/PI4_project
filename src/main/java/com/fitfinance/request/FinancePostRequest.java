package com.fitfinance.request;

import com.fitfinance.domain.FinanceType;
import com.fitfinance.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
public class FinancePostRequest {
    @NotBlank(message = "The field 'name' is required")
    @Schema(description = "Finance's name", example = "Cachaça")
    private String name;
    @NotBlank(message = "The field 'value' is required")
    @Schema(description = "Finance's value", example = "150.00")
    private double value;
    @NotBlank(message = "The field 'type' is required")
    @Schema(description = "Finance's type", example = "Income")
    private FinanceType type;
    @NotBlank(message = "The field 'description' is required")
    @Schema(description = "Finance's description", example = "Finance...")
    private String description;
    @NotBlank(message = "The field 'startDate' is required")
    private LocalDate startDate;
    @NotBlank(message = "The field 'endDate' is required")
    private LocalDate endDate;
    @NotBlank(message = "The field 'user' is required")
    @Schema(description = "Finance's user", example = "User")
    private User user;
}
