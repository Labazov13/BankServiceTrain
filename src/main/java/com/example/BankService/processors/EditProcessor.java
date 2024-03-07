package com.example.BankService.processors;

import com.example.BankService.model.Client;
import com.example.BankService.service.ClientService;
import com.example.BankService.service.LoginManagerService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Data
@RequestScope
public class EditProcessor {
    private LoginManagerService loginManagerService;
    private ClientService clientService;

    public EditProcessor(LoginManagerService loginManagerService, ClientService clientService) {
        this.loginManagerService = loginManagerService;
        this.clientService = clientService;
    }

    public Client getClient(String userEmail){
        var clients = clientService.findAll();
        for (Client client : clients){
            if (client.getEmail().equals(userEmail)){
                return client;
            }
        }
        return null;
    }
    public void editUser(Client client, String phone, String email){
        client.setPhone(phone);
        client.setEmail(email);
    }
}
