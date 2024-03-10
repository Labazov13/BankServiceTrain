package com.example.BankService.processors;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.service.LoginManagerService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Data
public class LoginProcessor {
    private ClientDAOImpl clientDAOImpl;
    private LoginManagerService loginManagerService;
    private String email;
    @Autowired
    public LoginProcessor(LoginManagerService loginManagerService, ClientDAOImpl clientDAOImpl) {
        this.loginManagerService = loginManagerService;
        this.clientDAOImpl = clientDAOImpl;
    }

    public boolean login(){
        var clients = clientDAOImpl.getAllClientDetails();
        boolean isLogged = false;
        for (ClientDetails client : clients){
            if (email.equals(client.getClient().getEmail())){
                isLogged = true;
                loginManagerService.setEmail(email);
                loginManagerService.setID(client.getID());
            }
        }
        return isLogged;
    }
}
