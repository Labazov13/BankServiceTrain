package com.example.BankService.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Client {
    private String firstName;
    private String lastName;
    private String fatherName;
    private String phone;
    private String email;
    private LocalDate birthDay;
    private BankAccount bankAccount;


    public Client(String firstName, String lastName, String fatherName, String phone, String email, LocalDate birthDay, BankAccount bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.phone = phone;
        this.email = email;
        this.birthDay = birthDay;
        this.bankAccount = bankAccount;
    }
}
