package com.example.BankService.processors;

import com.example.BankService.model.Client;
import com.example.BankService.service.ClientService;
import com.example.BankService.service.LoginManagerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Component
@RequestScope
@Data
public class LoginProcessor {
    private ClientService clientService;
    private LoginManagerService loginManagerService;
    private String email;
    @Autowired
    public LoginProcessor(ClientService clientService, LoginManagerService loginManagerService) {
        this.clientService = clientService;
        this.loginManagerService = loginManagerService;
    }

    public boolean login(){
        var clients = clientService.findAll();
        boolean isLogged = false;
        for (Client client : clients){
            if (email.equals(client.getEmail())){
                isLogged = true;
                loginManagerService.setEmail(email);
                loginManagerService.setID(client.getBankAccount().getId());
            }
        }
        return isLogged;
    }
}
