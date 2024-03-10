package com.example.BankService.dao;

import com.example.BankService.entity.ClientDetails;

import java.util.List;

public interface ClientDAO {
    void addClient(ClientDetails clientDetails);
    void updateClient(ClientDetails clientDetails);
    void deleteClient(ClientDetails clientDetails);
    ClientDetails getClientDetailsById(long id);
    List<ClientDetails> getAllClientDetails();
}
