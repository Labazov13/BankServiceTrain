package com.example.BankService.processors;

import com.example.BankService.model.Client;
import com.example.BankService.service.ClientService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Data
@RequestScope
public class HomeProcessor {
    private ClientService clientService;
    @Autowired
    public HomeProcessor(ClientService clientService) {
        this.clientService = clientService;
    }

    public Client findClient(String email) {
        var clients = clientService.findAll();
        for (Client client : clients){
            if (email.equals(client.getEmail())){
                return client;
            }
        }
        return null;
    }
}
