package com.example.BankService.entity;

import com.example.BankService.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Entity
@Table(name = "clients_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientDetails {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ID;
    @Embedded
    private Client client;

}
