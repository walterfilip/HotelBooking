package org.example.pensionat.customer.client;


import org.example.pensionat.customer.model.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class CustomerIntegrationTest {

    @Autowired CustomerClient customerClient;

    @Test
    void shouldGetFirstCustomerName(){
        CustomerResponse customer = customerClient.getCustomer(1L);

        assertNotNull(customer);
        assertEquals("Nils", customer.firstName());

    }

}
