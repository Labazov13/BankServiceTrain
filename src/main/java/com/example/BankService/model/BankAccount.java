package com.example.BankService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@Embeddable
@AllArgsConstructor
public class BankAccount {
    @Column(name = "bank_account_id")
    private long id;
    @Column(name = "initial_balance")
    private float initialBalance;
    @Column(name = "balance")
    private float accountBalance;
}
