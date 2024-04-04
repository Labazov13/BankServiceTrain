package com.example.BankService.service;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.entity.ClientDetailsImpl;
import com.example.BankService.model.Client;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Data
@ToString
public class ClientService implements UserDetailsService {

    @Autowired
    private ClientDAOImpl clientDAO;

    public ClientService(ClientDAOImpl clientDAO) {
        this.clientDAO = clientDAO;
    }

    BankAccountService bankAccountService = new BankAccountService();

    List<Client> clientList = new ArrayList<>();


    public List<Client> findAll() {
        return clientList;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClientDetails clientDetails = clientDAO.findByUsername(username);
        if (clientDetails == null){
            throw new UsernameNotFoundException("Not found client");
        }
        return new ClientDetailsImpl(clientDetails);
    }
}
