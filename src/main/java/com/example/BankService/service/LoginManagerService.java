package com.example.BankService.service;

import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
@Data
public class LoginManagerService {
    private String email;
    private long ID;
}
