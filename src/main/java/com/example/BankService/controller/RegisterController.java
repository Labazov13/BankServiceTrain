package com.example.BankService.controller;

import com.example.BankService.model.BankAccount;
import com.example.BankService.model.Client;
import com.example.BankService.processors.RegisterProcessor;
import com.example.BankService.service.BankAccountService;
import com.example.BankService.service.ClientService;
import com.example.BankService.service.LoginManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RegisterController {
    private RegisterProcessor registerProcessor;
    private ClientService clientService;
    private BankAccountService bankAccountService;
    private LoginManagerService loginManagerService;

    @Autowired
    public RegisterController(RegisterProcessor registerProcessor,
                              ClientService clientService,BankAccountService bankAccountService, LoginManagerService loginManagerService) {
        this.registerProcessor = registerProcessor;
        this.clientService = clientService;
        this.bankAccountService = bankAccountService;
        this.loginManagerService = loginManagerService;
    }

    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @PostMapping("/register")
    public String newRegister(@RequestParam String firstName,
                              @RequestParam String lastName,
                              @RequestParam String fatherName,
                              @RequestParam String phone,
                              @RequestParam String email,
                              @RequestParam LocalDate birthDay,
                              Model model){
        LocalDate currentMaxYear = LocalDate.of(1903, 1, 1);
        LocalDate nowYear = LocalDate.now();
        if (birthDay.getYear() < currentMaxYear.getYear() || birthDay.getYear() >= nowYear.getYear()){
            model.addAttribute("message", "Incorrect date entry");
            return "register.html";
        }
        else {
            registerProcessor.setEmail(email);
            boolean register = registerProcessor.register();
            if(register){
                BankAccount bankAccount = bankAccountService.createBankAccount();
                Client client = new Client(firstName, lastName, fatherName, phone, email, birthDay, bankAccount);
                clientService.addClient(client);
                loginManagerService.setID(client.getBankAccount().getId());
                var clients = clientService.findAll();
                model.addAttribute("clients", clients);
                return "redirect:/login";
            }
            else {
                model.addAttribute("message", "A user with this email address already exists");
                return "register.html";
            }
        }
    }
}























/*@PostMapping("/register")
    public String newRegister(@ModelAttribute Client client, Model model){
        registerProcessor.setEmail(client.getEmail());
        boolean register = registerProcessor.register();
        if(register){
            BankAccount bankAccount = bankAccountService.createBankAccount();
            client.setBankAccount(bankAccount);
            clientService.addClient(client);
            var clients = clientService.findAll();
            model.addAttribute("clients", clients);
            return "redirect:/login";
        }
        else {
            model.addAttribute("message", "A user with this email address already exists");
            return "register.html";
        }
    }*/












/*private final ClientService clientService;
    private final BankAccountService bankAccountService;
    private final RegisterProcessor registerProcessor;
    private final LoginManagerService loginManagerService;
    @Autowired
    public RegisterController(ClientService clientService, BankAccountService bankAccountService, RegisterProcessor registerProcessor, LoginManagerService loginManagerService) {
        this.clientService = clientService;
        this.bankAccountService = bankAccountService;
        this.registerProcessor = registerProcessor;
        this.loginManagerService = loginManagerService;
    }

    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @PostMapping("/register")
    public String newClient(@RequestParam String firstName,
                            @RequestParam String lastName,
                            @RequestParam String fatherName,
                            @RequestParam String phone,
                            @RequestParam String email,
                            Model model){
        registerProcessor.setEmail(email);
        boolean register = registerProcessor.register();
        if (register){
            Client client = new Client(firstName,
                    lastName,
                    fatherName,
                    email,
                    phone,
                    bankAccountService.createBankAccount(),
                    LocalDateTime.of(1996, Month.AUGUST, 15, 0, 0));
            clientService.addClient(client);
            loginManagerService.setEmail(email);
            //model.addAttribute("email", email);
            return "redirect:/home";
        }
        else {
            return "redirect:/";
        }
    }*/
