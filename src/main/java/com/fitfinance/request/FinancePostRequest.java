package com.fitfinance.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitfinance.domain.FinanceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@ToString
public class FinancePostRequest {
    @NotBlank(message = "The field 'name' is required")
    @Schema(description = "Finance's name", example = "Cachaça")
    private String name;
    @NotBlank(message = "The field 'value' is required")
    @Schema(description = "Finance's value", example = "150.00")
    private double value;
    @NotBlank(message = "The field 'type' is required")
    @Schema(description = "Finance's type", example = "INCOME")
    private FinanceType type;
    @NotBlank(message = "The field 'description' is required")
    @Schema(description = "Finance's description", example = "Finance...")
    private String description;
    @NotBlank(message = "The field 'startDate' is required")
    @JsonProperty("start_date")
    private LocalDate startDate;
    @NotBlank(message = "The field 'endDate' is required")
    @JsonProperty("end_date")
    private LocalDate endDate;
    @Schema(description = "Finance's user", example = "User")
    @JsonIgnore
    private UserPostRequest user;
}
