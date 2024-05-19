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
    @Schema(description = "User's first name", example = "Menor")
    private String firstName;
    @Schema(description = "User's last name", example = "Oakley")
    private String lastName;
    @Schema(description = "User's email", example = "lucas.oakley@fitfinance.com")
    private String email;
    @Schema(description = "User's role", example = "Driver")
    private String role;
    @Schema(description = "User's phone", example = "55 11 999999999")
    private String phone;
    @Schema(description = "User's income", example = "1500.00")
    private double income;
}
