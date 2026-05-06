package org.example.hotelbooking.models;

import lombok.Data;

@Data
public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;

    public Customer(int id, String firstName, String lastName, String email, int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }


}
