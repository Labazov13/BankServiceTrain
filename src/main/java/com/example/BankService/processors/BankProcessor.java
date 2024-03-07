package com.example.BankService.processors;

import com.example.BankService.model.Client;
import com.example.BankService.service.ClientService;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BankProcessor {
    private ClientService clientService;

    public BankProcessor(ClientService clientService) {
        this.clientService = clientService;
    }

    public boolean withdraw(String email, float amount){
        var clients = clientService.findAll();
        for (Client client : clients){
            if (client.getEmail().equals(email)){
                if (client.getBankAccount().getAccountBalance() >= amount){
                    client.getBankAccount().setAccountBalance(client.getBankAccount().getAccountBalance() - amount);
                    client.getBankAccount().setInitialBalance(client.getBankAccount().getInitialBalance() - amount);
                }
                return true;
            }
        }
        return false;
    }

    public boolean transfer(String fromEmail, String toEmail, float amount){
        Client fromClient = clientService.findByEmail(fromEmail);
        Client toClient = clientService.findByEmail(toEmail);
        float fromBalance = fromClient.getBankAccount().getAccountBalance();
        float toBalance = toClient.getBankAccount().getAccountBalance();
        if (fromBalance >= amount){
            fromClient.getBankAccount().setAccountBalance(fromBalance - amount);
            toClient.getBankAccount().setAccountBalance(toBalance + amount);
            return true;
        }
        return false;
    }
}
