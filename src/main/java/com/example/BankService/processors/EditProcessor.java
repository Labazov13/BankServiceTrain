package com.example.BankService.processors;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.service.LoginManagerService;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@Data
@RequestScope
public class EditProcessor {
    private LoginManagerService loginManagerService;
    private ClientDAOImpl clientDAOImpl;

    public EditProcessor(LoginManagerService loginManagerService, ClientDAOImpl clientDAOImpl) {
        this.loginManagerService = loginManagerService;
        this.clientDAOImpl = clientDAOImpl;
    }

    public ClientDetails getClient(String userEmail){
        var clients = clientDAOImpl.getAllClientDetails();
        for (ClientDetails client : clients){
            if (client.getClient().getEmail().equals(userEmail)){
                return client;
            }
        }
        return null;
    }
    public boolean editUser(ClientDetails client, String phone, String email){
        var clients = clientDAOImpl.getAllClientDetails();
        if (phone == null){
            client.getClient().setPhone(client.getClient().getPhone());
        }
        if (email == null){
            client.getClient().setEmail(client.getClient().getEmail());
        }
        for (ClientDetails clientDetails : clients){
            if (clientDetails.getClient().getPhone().equals(phone) || clientDetails.getClient().getEmail().equals(email)){
                return false;
            }
        }
        clientDAOImpl.updateClient(client);
        loginManagerService.setEmail(email);
        return true;
    }
}
