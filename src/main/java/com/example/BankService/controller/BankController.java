package com.example.BankService.controller;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.entity.ClientDetails;
import com.example.BankService.processors.BankProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BankController {
    private final BankProcessor bankProcessor;
    private final ClientDAOImpl clientDAOImpl;
    @Autowired
    public BankController(BankProcessor bankProcessor, ClientDAOImpl clientDAOImpl) {
        this.bankProcessor = bankProcessor;
        this.clientDAOImpl = clientDAOImpl;
    }

    @GetMapping("/withdraw")
    public String getWithdraw(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails = clientDAOImpl.findByUsername(auth.getName());
        model.addAttribute("email", clientDetails.getClient().getEmail());
        return "withdraw";
    }

    @PostMapping("/withdraw")
    public String postWithdraw(@RequestParam float amount, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails = clientDAOImpl.findByUsername(auth.getName());
        boolean isWithdraw = bankProcessor.withdraw(clientDetails, amount);
        if (isWithdraw){
            model.addAttribute("message", "Done!");
            return "redirect:/home";
        }
        else {
            model.addAttribute("message", "Insufficient funds");
            return "withdraw";
        }
    }

    @GetMapping("/transfer")
    public String getTransfer(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails clientDetails = clientDAOImpl.findByUsername(auth.getName());
        model.addAttribute("email", clientDetails.getClient().getEmail());
        model.addAttribute("balance", clientDetails.getClient().getBankAccount().getAccountBalance());
        return "transfer";
    }
    @PostMapping("/transfer")
    public String postTransfer(@RequestParam float amount, @RequestParam String toEmail, Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        ClientDetails fromClient = clientDAOImpl.findByUsername(auth.getName());
        ClientDetails toClient = clientDAOImpl.getClientDetailsByEmail(toEmail);
        if(toClient == null){
            model.addAttribute("email", fromClient.getClient().getEmail());
            model.addAttribute("balance", fromClient.getClient().getBankAccount().getAccountBalance());
            model.addAttribute("message", "This client does not exist");
            return "transfer";
        }
        if (toEmail.equals(fromClient.getClient().getEmail())){
            model.addAttribute("email", fromClient.getClient().getEmail());
            model.addAttribute("balance", fromClient.getClient().getBankAccount().getAccountBalance());
            model.addAttribute("message", "It is impossible to transfer money to yourself");
            return "transfer";
        }
        else {
            boolean isTransfer = bankProcessor.transfer(fromClient, toClient, amount);
            if (isTransfer){
                return "redirect:/home";
            }
            else {
                model.addAttribute("email", fromClient.getClient().getEmail());
                model.addAttribute("balance", fromClient.getClient().getBankAccount().getAccountBalance());
                model.addAttribute("message", "Insufficient funds");
                return "transfer";
            }
        }
    }
}
