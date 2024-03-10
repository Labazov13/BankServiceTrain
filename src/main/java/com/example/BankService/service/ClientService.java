package com.example.BankService.service;

import com.example.BankService.model.Client;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Data
@ToString
public class ClientService{

    BankAccountService bankAccountService = new BankAccountService();

    List<Client> clientList = new ArrayList<>();

    public ClientService() {
        Client c1 = new Client("qq", "qq", "qq", "qq", "qq",
                LocalDate.of(1996, 11, 1), bankAccountService.createBankAccount());
        Client c2 = new Client("aa", "aa", "aa", "aa", "aa",
                LocalDate.of(1996, 11, 1), bankAccountService.createBankAccount());
        clientList.add(c1);
        clientList.add(c2);
    }

    public void addClient(Client client){
        clientList.add(client);
    }

    public List<Client> findAll() {
        return clientList;
    }

    public Client findByEmail(String email){
        var clients = findAll();
        for(Client client : clients){
            if (client.getEmail().equals(email)){
                return client;
            }
        }
        return null;
    }
    public Client findByID(long ID){
        var clients = findAll();
        for(Client client : clients){
            if (client.getBankAccount().getId() == ID){
                return client;
            }
        }
        return null;
    }
}
