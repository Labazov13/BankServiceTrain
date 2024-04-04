package com.example.BankService.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class BankAccount {
    @Column(name = "bank_account_id")
    private Long id;
    @Column(name = "initial_balance")
    private float initialBalance;
    @Column(name = "balance")
    private float accountBalance;

}
