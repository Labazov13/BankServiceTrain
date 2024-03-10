package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.processors.LoginProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final LoginProcessor loginProcessor;
    private final ClientDAOImpl clientDAOImpl;
    @Autowired
    public LoginController(LoginProcessor loginProcessor, ClientDAOImpl clientDAOImpl) {
        this.loginProcessor = loginProcessor;
        this.clientDAOImpl = clientDAOImpl;
    }
    @GetMapping("/")
    public String main(Model model){
        var clients = clientDAOImpl.getAllClientDetails();
        model.addAttribute("clients", clients);
        return "login.html";
    }
    @GetMapping("/login")
    public String login(Model model){
        var clients = clientDAOImpl.getAllClientDetails();
        model.addAttribute("clients", clients);
        return "login.html";
    }

    @PostMapping("/")
    public String auth(@RequestParam String email, Model model){
        var clients = clientDAOImpl.getAllClientDetails();
        model.addAttribute("clients", clients);
        loginProcessor.setEmail(email);
        boolean login = loginProcessor.login();
        if(login){
            return "redirect:/home";
        }
        else {
            model.addAttribute("message", "No such user");
            return "login.html";
        }
    }
}
