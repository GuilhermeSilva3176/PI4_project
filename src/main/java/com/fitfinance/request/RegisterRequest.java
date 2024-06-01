package com.fitfinance.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Schema(description = "User's name", example = "John Doe")
    private String name;
    @Schema(description = "User's CPF", example = "123.456.789-00")
    private String cpf;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,8}$", message = "The email format is not valid")
    private String email;
    @NotBlank(message = "The field 'password' is required")
    private String password;
    @Schema(description = "User's phone", example = "+55 11 91234-5678")
    private String phone;
    @Schema(description = "User's birthdate", example = "1990-01-01")
    private String birthdate;
    @Schema(description = "User's income", example = "3500.00")
    private double income;
}
