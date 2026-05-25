package org.example.pensionat.customer.service;


import org.example.pensionat.booking.repository.BookingRepository;
import org.example.pensionat.customer.model.CreateCustomerRequest;
import org.example.pensionat.customer.model.Customer;
import org.example.pensionat.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private BookingRepository bookingRepository;


    @InjectMocks
    private CustomerService customerService;


    private Customer customer1 = new Customer();
    private Customer customer2 = new Customer();
    private List<Customer> fakeCustomers;



    @BeforeEach
    void setUp() {
        customer1 = new Customer(
                "Yayha",
                "hussien",
                "yahja@test.se",
                "0701111111",
                "hoppa"
        );

        customer2 = new Customer(
                "klas",
                "jensen",
                "klasse@test.se",
                "0701221111",
                "loppa"
        );

        fakeCustomers = List.of(
                customer1,
                customer2
        );
    }


    @Test
    void getAllCustomers_ShouldReturnAllCustomersFromRepository() {
        // Arrange
        when(customerRepository.findAll())
                .thenReturn(fakeCustomers);
        //Act

        Customer customer1 = new Customer(
                "Nils",
                "Modig",
                "nils@fakemail.se",
                "0767777777",
                "loppa"
        );

        Customer customer2 = new Customer(
                "Rebecca",
                "Eriksson",
                "rebecca@fakemail.se",
                "0767777776",
                "loppa"
        );


        List<Customer> fakeCustomers = List.of(customer1, customer2);

        when(customerRepository.findAll()).thenReturn(fakeCustomers);

        List<Customer> result = customerService.getAllCustomers();

        //assert
        assertThat(result)
                .hasSize(2);
//          assertEquals(result, fakeCustomers); //??
        assertThat(result)
                .containsExactly(
                        customer1,
                        customer2
                );
        assertThat(result).hasSize(2);

        assertThat(result).containsExactly(customer1, customer2);

        verify(customerRepository).findAll();
        assertThat(result.get(0).getFirstName()).isEqualTo("Yayha");

        verify(customerRepository)
                .findAll();
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
                        "070132546",
                        "loppa"
                );
        Customer savedCustomer = new Customer(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.phoneNumber(),
                request.password()
        );

        when(customerRepository.save(any(Customer.class)))
                .thenReturn(savedCustomer);
        Customer result = customerService.createCustomer(request);
        assertThat(result.getFirstName()).isEqualTo("Jens");
        verify(customerRepository)
                .save(any(Customer.class));
    }
//        when(customerRepository.save(testCustomer))
//                .thenReturn(testCustomer);
//        assertThat(testCustomer.getFirstName()).isEqualTo("Jens");
//        assertThat(testCustomer.getLastName()).isEqualTo("Kodbengtsson");



    //TODO create test for createUser Method

    // method saves users
    // method saves correct user



}


// mock fejkat objekt

// stub förbereder ett svar (when(repo.findAll()).then ....

//fake implementartion av fejkade objekt



//        Customer customer1 = new Customer(
//                "Yayha",
//                "hussien",
//                "yahja@test.se",
//                "0701111111"
//        );
//
//        Customer customer2 = new Customer(
//                "klas",
//                "jensen",
//                "klasse@test.se",
//                "0701221111"
//        );

//        List<Customer> fakeCustomers = List.of(
//                customer1,
//                customer2
//        );


//        Customer customer1 = new Customer(
//                "Yayha",
//                "hussien",
//                "yahja@test.se",
//                "0701111111"
//        );
//
//        Customer customer2 = new Customer(
//                "klas",
//                "jensen",
//                "klasse@test.se",
//                "0701221111"
//        );
//
//    }
