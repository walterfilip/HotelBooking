package org.example.pensionat.customer.service;


import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {



    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookingRepository bookingRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void getAllCustomers_ShouldReturnAllCustomersFromRepository() {

        Customer customer1 = new Customer(
                "Nils",
                "Modig",
                "nils@fakemail.se",
                "0767777777"
        );

        Customer customer2 = new Customer(
                "Rebecca",
                "Eriksson",
                "rebecca@fakemail.se",
                "0767777776"
        );


        List<Customer> fakeCustomers = List.of(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(fakeCustomers);

        List<Customer> result = customerService.getAllCustomers();

        assertThat(result).hasSize(2);

        assertThat(result).containsExactly(customer1, customer2);

        verify(customerRepository).findAll();

        verifyNoMoreInteractions(customerRepository);

        verifyNoMoreInteractions(bookingRepository);

    }
    @Test
    void testForCreateCustomer() {
        CreateCustomerRequest request =
                new CreateCustomerRequest(
                        "Jens",
                        "Kodbengtsson",
                        "kodarjesse@mail.com",
                        "070132546"
                );
        Customer savedCustomer = new Customer(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phoneNumber()
        );

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(savedCustomer);
        Customer result = customerService.createCustomer(request);
        assertThat(result.getFirstName()).isEqualTo("Jens");
        verify(customerRepository)
                .save(any(Customer.class));
    }


}
