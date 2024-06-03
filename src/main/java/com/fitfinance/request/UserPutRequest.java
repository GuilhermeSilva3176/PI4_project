package com.fitfinance.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class UserPutRequest {
    @NotNull
    private Long id;
    @NotBlank(message = "The field 'name' is required")
    private String name;
    @NotBlank(message = "The field 'CPF' is required")
    private String cpf;
    private String password;
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,8}$", message = "The email format is not valid")
    private String email;
    @NotBlank(message = "The field 'phone' is required")
    private String phone;
    @NotBlank(message = "The field 'birthdate' is required")
    private String birthdate;
    @NotBlank(message = "The field 'income' is required")
    private double income;
}
