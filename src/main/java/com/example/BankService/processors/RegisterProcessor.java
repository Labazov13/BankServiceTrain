package com.example.BankService.processors;

import com.example.BankService.model.Client;
import com.example.BankService.service.ClientService;
import com.example.BankService.service.LoginManagerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class RegisterProcessor {
    private ClientService clientService;
    private LoginManagerService loginManagerService;
    private String email;
    @Autowired
    public RegisterProcessor(ClientService clientService, LoginManagerService loginManagerService) {
        this.clientService = clientService;
        this.loginManagerService = loginManagerService;
    }

    public boolean register(){
        var clients = clientService.findAll();
        boolean notRegister = false;
        for (Client client : clients){
            if(email.equals(client.getEmail())){
                return false;
            }
        }
        loginManagerService.setEmail(email);
        notRegister = true;
        return notRegister;
    }
}












/*private ClientService clientService;
    private LoginManagerService loginManagerService;
    private String email;

    @Autowired
    public RegisterProcessor(ClientService clientService, LoginManagerService loginManagerService) {
        this.clientService = clientService;
        this.loginManagerService = loginManagerService;
    }

    public boolean register(){
        var clients = clientService.findAll();
        boolean notRegister = false;
        for (Client client : clients){
            if (email.equals(client.getEmail())){
                notRegister = false;
            }
            else {
                notRegister = true;
                loginManagerService.setEmail(email);
            }
        }
        return notRegister;
    }*/