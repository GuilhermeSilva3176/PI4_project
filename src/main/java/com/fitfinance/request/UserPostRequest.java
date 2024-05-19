package com.fitfinance.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserPostRequest {
    @NotBlank(message = "The field 'firstName' is required")
    @Schema(description = "User's first name", example = "Menor")
    private String firstName;
    @NotBlank(message = "The field 'lastName' is required")
    @Schema(description = "User's last name", example = "Oakley")
    private String lastName;
    @NotBlank(message = "The field 'username' is required")
    @Schema(description = "User's username", example = "lucas_oakley")
    private String username;
    @NotBlank(message = "The field 'password' is required")
    private String password;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,8}$", message = "The email format is not valid")
    @Schema(description = "User's email", example = "lucas.oakley@fitfinance.com")
    private String email;
    @NotBlank(message = "The field 'role' is required")
    @Schema(description = "User's role", example = "Driver")
    private String role;
    @NotBlank(message = "The field 'phone' is required")
    @Schema(description = "User's phone", example = "+55 11 91234-5678")
    private String phone;
    @NotBlank(message = "The field 'income' is required")
    @Schema(description = "User's income", example = "3500.00")
    private double income;
}
