package com.example.BankService.configurations;

import com.example.BankService.entity.ClientDetails;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfig {
    @Bean
    public SessionFactory getSessionFactory() {
        return new org.hibernate.cfg.Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(ClientDetails.class)
                .buildSessionFactory();
    }

}
