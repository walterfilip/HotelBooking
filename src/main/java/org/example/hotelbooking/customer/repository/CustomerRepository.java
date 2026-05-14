package org.example.hotelbooking.customer.repository;


import org.example.hotelbooking.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer,Long> {


}
