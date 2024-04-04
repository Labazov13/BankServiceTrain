package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.model.BankAccount;
import com.example.BankService.model.Client;
import com.example.BankService.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final BankAccountService bankAccountService;
    private final ClientDAOImpl clientDAOImpl;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(BankAccountService bankAccountService, ClientDAOImpl clientDAOImpl, PasswordEncoder passwordEncoder) {
        this.bankAccountService = bankAccountService;
        this.clientDAOImpl = clientDAOImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute("client", new Client());
        return "register";
    }

    @PostMapping
    public String newRegister(@ModelAttribute("client") Client client, Model model) {
            client.setPassword(passwordEncoder.encode(client.getPassword()));
            client.setRoles("ROLE_USER");
            BankAccount bankAccount = bankAccountService.createBankAccount();
            client.setBankAccount(bankAccount);
            ClientDetails clientDetails = new ClientDetails();
            clientDetails.setClient(client);
            clientDAOImpl.addClientDetails(clientDetails);
            var clients = clientDAOImpl.getAllClientDetails();
            model.addAttribute("clients", clients);
            return "redirect:/loging";
    }
}