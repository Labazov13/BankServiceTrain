package com.example.BankService.dao;

import com.example.BankService.entity.ClientDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientDAO {
    void addClientDetails(ClientDetails clientDetails);
    ClientDetails findByUsername(String username);
    void updateClient(ClientDetails clientDetails);
    void deleteClient(ClientDetails clientDetails);
    ClientDetails getClientDetailsById(long id);
    List<ClientDetails> getAllClientDetails();
}
