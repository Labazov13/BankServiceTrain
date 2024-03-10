package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.service.LoginManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    private final LoginManagerService loginManagerService;
    private final ClientDAOImpl clientDAOImpl;
    @Autowired
    public HomeController(LoginManagerService loginManagerService, ClientDAOImpl clientDAOImpl) {
        this.loginManagerService = loginManagerService;
        this.clientDAOImpl = clientDAOImpl;
    }

    @GetMapping("/home")
    public String home(Model model, @RequestParam(required = false) String Logout){
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        if (Logout != null){
            loginManagerService.setEmail(null);
        }
        long ID = loginManagerService.getID();
        String email = loginManagerService.getEmail();
        ClientDetails searchClient = clientDAOImpl.getClientDetailsById(ID);
        String firstName = searchClient.getClient().getFirstName();
        String lastName = searchClient.getClient().getLastName();
        String id = String.valueOf(searchClient.getClient().getBankAccount().getId());
        String balance = String.valueOf(searchClient.getClient().getBankAccount().getAccountBalance());
        model.addAttribute("email", email);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("id", id);
        model.addAttribute("balance", balance);
        return "home.html";
    }
}
