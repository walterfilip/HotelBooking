package org.example.pensionat.customer.repository;

import org.example.pensionat.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer,Long> {

     Customer findByEmail(String email);




}
