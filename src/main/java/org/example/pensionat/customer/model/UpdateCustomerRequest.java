package org.example.pensionat.customer.model;


public record UpdateCustomerRequest(
        CreateCustomerRequest request,
        boolean changePassword
){

}
