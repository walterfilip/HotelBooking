package org.example.pensionat.customer;

import org.example.pensionat.customer.dto.*;
import org.example.pensionat.customer.dto.UpdateCustomerRequest;
import org.example.pensionat.customer.model.*;

public interface CustomerClient {
    CustomerResponse login(LoginRequest request);
    CustomerResponse getCustomer (long customer);
    CustomerResponse createCustomer(CreateCustomerRequest request);

    CustomerResponse updateCustomer(long customerId, UpdateCustomerRequest request);

    //       CustomerResponse getCustomerByEmail(String email);
    //    void deleteCustomer(long customerId);
}
