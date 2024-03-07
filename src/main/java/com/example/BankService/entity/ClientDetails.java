package com.example.BankService.entity;

import com.example.BankService.model.BankAccount;
import com.example.BankService.model.Client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ClientDetails {
    @Id
    @Column(name = "ID")
    private long ID;
    private Client client;
    private BankAccount bankAccount;

    public ClientDetails(Client client, BankAccount bankAccount) {
        this.client = client;
        this.bankAccount = bankAccount;
    }

    public ClientDetails() {
    }
}
