package com.example.BankService.service;

import com.example.BankService.model.BankAccount;
import com.example.BankService.model.Client;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@Data
public class BankAccountService {
    private BankAccount bankAccount;
    private List<BankAccount> bankAccounts;
    @Autowired
    private ClientService clientService;


    public BankAccount createBankAccount(){
        Random random = new Random();
        long id = random.nextLong(0, 146240000);
        float initialAmount = 5000;
        float amount = initialAmount;
        return new BankAccount(id, initialAmount, amount);
    }
    public List<BankAccount> findAll(){
        return bankAccounts;
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void accrualPercent() throws InterruptedException {
        List<Client> clients = clientService.findAll();
        for (Client client : clients){
            float currentBalance = client.getBankAccount().getAccountBalance();
            float newBalance = currentBalance * 1.05f;
            if (newBalance <= client.getBankAccount().getInitialBalance() * 2.07f){
                client.getBankAccount().setAccountBalance(newBalance);
            }
        }
    }

}
