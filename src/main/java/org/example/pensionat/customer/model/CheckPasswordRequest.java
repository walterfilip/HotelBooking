package org.example.pensionat.customer.model;

public record CheckPasswordRequest(
        String password,
        String newPassword,
        String email
) {
}