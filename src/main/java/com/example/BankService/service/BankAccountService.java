package com.example.BankService.service;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.model.BankAccount;
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

    @Autowired
    private ClientService clientService;
    @Autowired
    private ClientDAOImpl clientDAOImpl;

    private Random random = new Random();


    public BankAccount createBankAccount(){

        long id = random.nextLong(0, 146240000);
        float initialAmount = random.nextFloat(0, 70000);
        float amount = initialAmount;
        return new BankAccount(id, initialAmount, amount);
    }

    @Scheduled(initialDelay = 60000, fixedDelay = 60000)
    public void accrualPercent() {
        List<ClientDetails> clientDetails = clientDAOImpl.getAllClientDetails();
        for (ClientDetails client : clientDetails){
            float currentBalance = client.getClient().getBankAccount().getAccountBalance();
            float newBalance = currentBalance * 1.05f;
            if (newBalance <= client.getClient().getBankAccount().getInitialBalance() * 2.07f){
                client.getClient().getBankAccount().setAccountBalance(newBalance);
                clientDAOImpl.updateClient(client);
            }
        }
    }
}
