package org.example.pensionat.customer.client;

import org.example.pensionat.customer.model.UpdateCustomerRequest;
import org.example.pensionat.customer.model.*;

public interface CustomerClient {
    CustomerResponse login(LoginRequest request);
    CustomerResponse getCustomer (long customer);
    CustomerResponse createCustomer(CreateCustomerRequest request);
    CustomerResponse updateCustomer(long customerId, UpdateCustomerRequest request);

    Boolean checkPassword(CheckPasswordRequest request);

    void deleteCustomer(long customerId);


    //       CustomerResponse getCustomerByEmail(String email);
}
