package com.example.BankService.controller;

import com.example.BankService.model.Client;
import com.example.BankService.processors.EditProcessor;
import com.example.BankService.service.ClientService;
import com.example.BankService.service.LoginManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditController {
    private LoginManagerService loginManagerService;
    private EditProcessor editProcessor;
    private ClientService clientService;
    @Autowired
    public EditController(LoginManagerService loginManagerService, EditProcessor editProcessor, ClientService clientService) {
        this.loginManagerService = loginManagerService;
        this.editProcessor = editProcessor;
        this.clientService = clientService;
    }

    @GetMapping("/edit")
    public String editUser(Model model){
        long ID = loginManagerService.getID();
        if(ID == 0){
            return "redirect:/";
        }
        Client client = clientService.findByID(ID);
        model.addAttribute("email", client.getEmail());
        model.addAttribute("phone", client.getPhone());
        return "edit.html";
    }

    @PostMapping("/edit")
    public String editClient(@RequestParam String phone, @RequestParam String email, Model model){
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        String userEmail = loginManagerService.getEmail();
        Client client = editProcessor.getClient(userEmail);
        editProcessor.editUser(client, phone, email);
        loginManagerService.setEmail(email);
        return "redirect:/home";
    }
}
