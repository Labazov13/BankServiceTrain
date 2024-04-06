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

    public boolean withdraw(ClientDetails clientDetails, float amount) {
        if (clientDetails.getClient().getBankAccount().getAccountBalance() >= amount) {
            clientDetails.getClient().getBankAccount()
                    .setAccountBalance(clientDetails.getClient().getBankAccount().getAccountBalance() - amount);
            clientDetails.getClient().getBankAccount()
                    .setInitialBalance(clientDetails.getClient().getBankAccount().getInitialBalance() - amount);
            clientDAOImpl.updateClient(clientDetails);
            return true;
        }
        return false;
    }

    public boolean transfer(ClientDetails fromClient, ClientDetails toClient, float amount) {
        float fromBalance = fromClient.getClient().getBankAccount().getAccountBalance();
        float toBalance = toClient.getClient().getBankAccount().getAccountBalance();
        if (fromBalance >= amount) {
            fromClient.getClient().getBankAccount().setAccountBalance(fromBalance - amount);
            toClient.getClient().getBankAccount().setAccountBalance(toBalance + amount);
            clientDAOImpl.updateClient(fromClient);
            clientDAOImpl.updateClient(toClient);
            return true;
        }
        return false;
    }
}
