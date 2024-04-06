package com.example.BankService.model;

import com.example.BankService.customAnnotations.BirthDate;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "Field not can be empty")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Field not can be empty")
    private String lastName;

    @Column(name = "father_name")
    @NotBlank(message = "Field not can be empty")
    private String fatherName;

    @Column(name = "phone")
    @NotBlank(message = "Field not can be empty")
    private String phone;

    @Column(name = "email")
    @NotBlank(message = "Field not can be empty")
    private String email;

    @Column(name = "username")
    @NotBlank(message = "Field not can be empty")
    private String username;

    @Column(name = "password")
    @NotBlank(message = "Field not can be empty")
    private String password;

    @Column(name = "birth_day")
    @BirthDate(message = "Incorrect year")
    private LocalDate birthDay;

    @Column(name = "roles")
    private String roles;

    @Embedded
    private BankAccount bankAccount;
}
