package com.example.BankService.controller;

import com.example.BankService.model.Client;
import com.example.BankService.processors.HomeProcessor;
import com.example.BankService.processors.LoginProcessor;
import com.example.BankService.service.ClientService;
import com.example.BankService.service.LoginManagerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final LoginManagerService loginManagerService;
    private ClientService clientService;
    @Autowired
    public HomeController(LoginManagerService loginManagerService, ClientService clientService) {
        this.loginManagerService = loginManagerService;
        this.clientService = clientService;
    }

    @GetMapping("/home")
    public String home(Model model, @RequestParam(required = false) String Logout){
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        if (Logout != null){
            loginManagerService.setEmail(null);
        }
        String email = loginManagerService.getEmail();
        Client searchClient = clientService.findByEmail(email);
        String firstName = searchClient.getFirstName();
        String lastName = searchClient.getLastName();
        String id = String.valueOf(searchClient.getBankAccount().getId());
        String balance = String.valueOf(searchClient.getBankAccount().getAccountBalance());
        model.addAttribute("email", email);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("id", id);
        model.addAttribute("balance", balance);
        return "home.html";
    }
}
