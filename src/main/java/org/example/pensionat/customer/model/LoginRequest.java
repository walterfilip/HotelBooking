package org.example.pensionat.customer.model;

public record LoginRequest(
        String email,
        String password
) {
}