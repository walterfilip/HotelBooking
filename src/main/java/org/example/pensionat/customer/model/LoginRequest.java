package org.example.pensionat.customer.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "E-post måste anges")
        @Email(message = "E-post måste vara giltig")
        String email,

        @NotBlank(message = "Lösenord måste anges")
        String password
) {
}