package com.fitfinance.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserGetResponse {
    @Schema(description = "User's id", example = "0001")
    private Long id;
    @Schema(description = "User's name", example = "Menor Oakley")
    private String name;
    @Schema(description = "User's CPF", example = "111.222.333-44")
    @JsonIgnore
    private String cpf;
    @Schema(description = "User's email", example = "lucas.oakley@fitfinance.com")
    private String email;
    @Schema(description = "User's phone", example = "55 11 999999999")
    @JsonIgnore
    private String phone;
    @Schema(description = "User's birthdate", example = "1990-01-01")
    @JsonIgnore
    private String birthdate;
    @Schema(description = "User's income", example = "1500.00")
    private double income;
}
