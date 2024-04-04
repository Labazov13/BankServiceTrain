package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    private final ClientDAOImpl clientDAOImpl;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public LoginController(ClientDAOImpl clientDAOImpl, PasswordEncoder passwordEncoder) {
        this.clientDAOImpl = clientDAOImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String main(Model model) {
        var clients = clientDAOImpl.getAllClientDetails();
        model.addAttribute("clients", clients);
        return "loging";
    }

    @GetMapping("/loging")
    public String login(Model model) {
        var clients = clientDAOImpl.getAllClientDetails();
        model.addAttribute("clients", clients);
        return "loging";
    }

    @PostMapping("/loging")
    public String auth(@RequestParam String username, @RequestParam String password, Model model) {
        var clients = clientDAOImpl.getAllClientDetails();
        model.addAttribute("clients", clients);
        ClientDetails clientDetails = clientDAOImpl.findByUsername(username);
        if (clientDetails != null && username.equals(clientDetails.getClient().getUsername())
                && passwordEncoder.matches(password, clientDetails.getClient().getPassword())) {
            return "redirect:/home";
        }
        else {
            model.addAttribute("message", "No such user");
            return "redirect:loging";
        }
    }
}
