package org.example.pensionat.customer.client;

import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CustomerIntegrationTest {

    private final RestTemplate restTemplate = new RestTemplate();

    @Test
    void registeringCustomerShouldReturn201() throws Exception {
        CreateCustomerRequest request = new CreateCustomerRequest(
                "Jens",
                "Jensson",
                "jens.test@fakemail.se",
                "0767661111",
                "hej"
        );

        CustomerResponse createdCustomer = null;

        try {
            ResponseEntity<CustomerResponse> response =
                    restTemplate.postForEntity(
                            "http://localhost:8081/api/customers",
                            request,
                            CustomerResponse.class
                    );

            assertEquals(201, response.getStatusCode().value());

            createdCustomer = response.getBody();

            assertNotNull(createdCustomer);
            assertTrue(createdCustomer.id() > 0);
            assertEquals("Jens", createdCustomer.firstName());
            assertEquals("Jensson", createdCustomer.lastName());
            assertEquals("jens.test@fakemail.se", createdCustomer.email());
            assertEquals("0767661111", createdCustomer.phoneNumber()
            );
        } finally {
            if (createdCustomer != null) {
                restTemplate.postForObject(
                        "http://localhost:8081/api/customers/delete",
                        createdCustomer.id(),
                        String.class
                );
            }
        }
    }
}
