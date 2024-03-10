package com.example.BankService.processors;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BankProcessor {
    private ClientDAOImpl clientDAOImpl;

    public BankProcessor(ClientDAOImpl clientDAOImpl) {
        this.clientDAOImpl = clientDAOImpl;
    }

    public boolean withdraw(String email, float amount){
        var clients = clientDAOImpl.getAllClientDetails();
        for (ClientDetails client : clients){
            if (client.getClient().getEmail().equals(email)){
                if (client.getClient().getBankAccount().getAccountBalance() >= amount){
                    client.getClient().getBankAccount()
                            .setAccountBalance(client.getClient().getBankAccount().getAccountBalance() - amount);
                    client.getClient().getBankAccount()
                            .setInitialBalance(client.getClient().getBankAccount().getInitialBalance() - amount);
                    clientDAOImpl.updateClient(client);
                }
                return true;
            }
        }
        return false;
    }

    public boolean transfer(String fromEmail, String toEmail, float amount){
        ClientDetails fromClient = clientDAOImpl.getClientDetailsByEmail(fromEmail);
        ClientDetails toClient = clientDAOImpl.getClientDetailsByEmail(toEmail);
        float fromBalance = fromClient.getClient().getBankAccount().getAccountBalance();
        float toBalance = toClient.getClient().getBankAccount().getAccountBalance();
        if (fromBalance >= amount){
            fromClient.getClient().getBankAccount().setAccountBalance(fromBalance - amount);
            toClient.getClient().getBankAccount().setAccountBalance(toBalance + amount);
            clientDAOImpl.updateClient(fromClient);
            clientDAOImpl.updateClient(toClient);
            return true;
        }
        return false;
    }
}
