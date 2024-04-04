package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    private final ClientDAOImpl clientDAOImpl;
    @Autowired
    public HomeController(ClientDAOImpl clientDAOImpl) {
        this.clientDAOImpl = clientDAOImpl;
    }

    @GetMapping("/home")
    public String home(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        ClientDetails clientDetails = clientDAOImpl.findByUsername(username);
        model.addAttribute("email", clientDetails.getClient().getEmail());
        model.addAttribute("username", clientDetails.getClient().getUsername());
        model.addAttribute("firstName", clientDetails.getClient().getFirstName());
        model.addAttribute("lastName", clientDetails.getClient().getLastName());
        model.addAttribute("id", clientDetails.getClient().getBankAccount().getId());
        model.addAttribute("balance", clientDetails.getClient().getBankAccount().getAccountBalance());
        return "home";
    }
}