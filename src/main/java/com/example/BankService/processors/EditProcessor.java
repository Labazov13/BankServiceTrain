package com.example.BankService.processors;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
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
        for (ClientDetails clientDetails : clients){
            if (clientDetails.getClient().getPhone().equals(phone) || clientDetails.getClient().getEmail().equals(email)){
                return false;
            }
        }
        String setPhone = phone.isEmpty() ? client.getClient().getPhone() : phone;
        String setEmail = email.isEmpty() ? client.getClient().getEmail() : email;
        client.getClient().setPhone(setPhone);
        client.getClient().setEmail(setEmail);
        clientDAOImpl.updateClient(client);
        return true;
    }
}
