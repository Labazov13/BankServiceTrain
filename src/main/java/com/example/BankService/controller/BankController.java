package com.example.BankService.controller;

import com.example.BankService.model.Client;
import com.example.BankService.processors.BankProcessor;
import com.example.BankService.service.ClientService;
import com.example.BankService.service.LoginManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankController {
    private LoginManagerService loginManagerService;
    private BankProcessor bankProcessor;
    private ClientService clientService;
    @Autowired
    public BankController(LoginManagerService loginManagerService, BankProcessor bankProcessor, ClientService clientService) {
        this.loginManagerService = loginManagerService;
        this.bankProcessor = bankProcessor;
        this.clientService = clientService;
    }

    @GetMapping("/withdraw")
    public String getWithdraw(Model model){
        String email = loginManagerService.getEmail();
        long ID = loginManagerService.getID();
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        model.addAttribute("email", email);
        return "withdraw.html";
    }

    @PostMapping("/withdraw")
    public String postWithdraw(@RequestParam float amount, Model model){
        String email = loginManagerService.getEmail();
        long ID = loginManagerService.getID();
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        boolean isWithdraw = bankProcessor.withdraw(email, amount);
        if (isWithdraw){
            model.addAttribute("message", "Done!");
            return "redirect:/home";
        }
        else {
            model.addAttribute("message", "Insufficient funds");
            return "withdraw.html";
        }
    }

    @GetMapping("/transfer")
    public String getTransfer(Model model){
        String email = loginManagerService.getEmail();
        long ID = loginManagerService.getID();
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        Client client = clientService.findByEmail(email);
        model.addAttribute("email", email);
        model.addAttribute("balance", client.getBankAccount().getAccountBalance());
        return "transfer.html";
    }
    @PostMapping("/transfer")
    public String postTransfer(@RequestParam float amount,
                               @RequestParam String toEmail,
                               Model model){
        String fromEmail = loginManagerService.getEmail();
        long ID = loginManagerService.getID();
        if(loginManagerService.getID() == 0){
            return "redirect:/";
        }
        Client fromClient = clientService.findByEmail(fromEmail);
        Client toClient = clientService.findByEmail(toEmail);
        if(toClient == null){
            model.addAttribute("email", fromEmail);
            model.addAttribute("balance", fromClient.getBankAccount().getAccountBalance());
            model.addAttribute("message", "This client does not exist");
            return "transfer.html";
        }
        if (toEmail.equals(fromEmail)){
            model.addAttribute("email", fromEmail);
            model.addAttribute("balance", fromClient.getBankAccount().getAccountBalance());
            model.addAttribute("message", "It is impossible to transfer money to yourself");
            return "transfer.html";
        }
        else {
            boolean isTransfer = bankProcessor.transfer(fromEmail, toEmail, amount);
            if (isTransfer){
                return "redirect:/home";
            }
            else {
                model.addAttribute("email", fromEmail);
                model.addAttribute("balance", fromClient.getBankAccount().getAccountBalance());
                model.addAttribute("message", "Insufficient funds");
                return "transfer.html";
            }
        }
    }
}
