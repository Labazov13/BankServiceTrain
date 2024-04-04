package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.processors.EditProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/edit")
public class EditController {
    private final EditProcessor editProcessor;
    private final ClientDAOImpl clientDAOImpl;
    @Autowired
    public EditController(EditProcessor editProcessor, ClientDAOImpl clientDAOImpl) {
        this.editProcessor = editProcessor;
        this.clientDAOImpl = clientDAOImpl;
    }

    @GetMapping
    public String editUser(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails client = clientDAOImpl.findByUsername(auth.getName());
        model.addAttribute("email", client.getClient().getEmail());
        model.addAttribute("phone", client.getClient().getPhone());
        return "edit";
    }

    @PostMapping
    public String editClient(@RequestParam(required = false) String phone,
                             @RequestParam(required = false) String email,
                             Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails client = clientDAOImpl.findByUsername(auth.getName());
        boolean isEdit = editProcessor.editUser(client, phone, email);
        if (isEdit){
            return "redirect:/home";
        }
        else {
            model.addAttribute("email", client.getClient().getEmail());
            model.addAttribute("phone", client.getClient().getPhone());
            model.addAttribute("message", "This email is already registered in the system");
            return "edit";
        }
    }
}
