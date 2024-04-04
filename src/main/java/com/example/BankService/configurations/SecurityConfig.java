package com.example.BankService.configurations;

import com.example.BankService.dao.ClientDAOImpl;
import com.example.BankService.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private ClientDAOImpl clientDAO;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    @Bean
    public UserDetailsService userDetailsService(ClientDAOImpl clientDA){
        return new ClientService(clientDAO);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((autorize) -> autorize
                        .requestMatchers("/edit", "/withdraw", "/transfer", "/home").authenticated()
                        .requestMatchers("/", "/loging").permitAll())
                .formLogin(form -> form.loginPage("/loging")
                        .defaultSuccessUrl("/home")).logout(logout -> logout.logoutSuccessUrl("/loging"));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService(clientDAO));
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

}
