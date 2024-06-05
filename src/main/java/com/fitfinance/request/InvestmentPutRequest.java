package com.fitfinance.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fitfinance.domain.FinanceType;
import com.fitfinance.domain.InvestmentType;
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
public class InvestmentPutRequest {
    @NotBlank(message = "The field 'id' is required")
    @Schema(description = "Investment's id", example = "1")
    private Long id;
    @NotBlank(message = "The field 'name' is required")
    @Schema(description = "Finance's name", example = "Cachaça")
    private String name;
    @NotBlank(message = "The field 'value' is required")
    @Schema(description = "Finance's value", example = "150.00")
    private Double price;
    @NotBlank(message = "The field 'type' is required")
    @Schema(description = "Investment's type", example = "STOCK")
    private InvestmentType type;
    @NotBlank(message = "The field 'quantity' is required")
    @Schema(description = "Investment's quantity", example = "1")
    private Integer quantity;
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
