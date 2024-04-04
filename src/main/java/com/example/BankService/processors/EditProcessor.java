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
    private ClientDAOImpl clientDAOImpl;

    public EditProcessor(ClientDAOImpl clientDAOImpl) {
        this.clientDAOImpl = clientDAOImpl;
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
        client.getClient().setPhone(phone);
        client.getClient().setEmail(email);
        clientDAOImpl.updateClient(client);
        return true;
    }
}
