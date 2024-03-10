package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.processors.EditProcessor;
import com.example.BankService.service.LoginManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EditController {
    private final LoginManagerService loginManagerService;
    private final EditProcessor editProcessor;
    private final ClientDAOImpl clientDAOImpl;
    @Autowired
    public EditController(LoginManagerService loginManagerService, EditProcessor editProcessor, ClientDAOImpl clientDAOImpl) {
        this.loginManagerService = loginManagerService;
        this.editProcessor = editProcessor;
        this.clientDAOImpl = clientDAOImpl;
    }

    @GetMapping("/edit")
    public String editUser(Model model){
        long ID = loginManagerService.getID();
        if(ID == 0){
            return "redirect:/";
        }
        ClientDetails client = clientDAOImpl.getClientDetailsById(ID);
        model.addAttribute("email", client.getClient().getEmail());
        model.addAttribute("phone", client.getClient().getPhone());
        return "edit.html";
    }

    @PostMapping("/edit")
    public String editClient(@RequestParam(required = false) String phone, @RequestParam(required = false) String email, Model model){
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        String userEmail = loginManagerService.getEmail();
        ClientDetails client = editProcessor.getClient(userEmail);
        boolean isEdit = editProcessor.editUser(client, phone, email);
        if (isEdit){
            return "redirect:/home";
        }
        else {
            model.addAttribute("email", userEmail);
            model.addAttribute("phone", client.getClient().getPhone());
            model.addAttribute("message", "This email is already registered in the system");
            return "edit.html";
        }

    }
}
