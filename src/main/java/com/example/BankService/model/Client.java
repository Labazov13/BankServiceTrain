package com.example.BankService.model;

import com.example.BankService.customAnnotations.BirthDate;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.time.LocalDate;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Client {
    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "father_name")
    private String fatherName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_day")
    //@BirthDate(message = "Incorrect year")
    private LocalDate birthDay;

    @Column(name = "roles")
    private String roles;


    @Embedded
    private BankAccount bankAccount;
}
