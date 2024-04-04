package com.example.BankService.entity;

import com.example.BankService.model.Client;
import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@Table(name = "clients_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ClientDetails{
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @Embedded
    private Client client;
}
