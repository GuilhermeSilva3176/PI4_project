package com.fitfinance.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserPostRoleAdminRequest {
    @NotBlank(message = "The field 'name' is required")
    @Schema(description = "User's name", example = "Menor Oakley")
    private String name;
    @NotBlank(message = "The field 'CPF' is required")
    @Schema(description = "User's CPF", example = "111.222.333-44")
    private String cpf;
    @NotBlank(message = "The field 'password' is required")
    private String password;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,8}$", message = "The email format is not valid")
    @Schema(description = "User's email", example = "lucas.oakley@fitfinance.com")
    private String email;
    @NotBlank(message = "The field 'phone' is required")
    @Schema(description = "User's phone", example = "+55 11 91234-5678")
    private String phone;
    @NotBlank(message = "The field 'birthdate' is required")
    @Schema(description = "User's birthdate", example = "1990-01-01")
    private String birthdate;
    @NotBlank(message = "The field 'income' is required")
    @Schema(description = "User's income", example = "3500.00")
    private double income;
}
