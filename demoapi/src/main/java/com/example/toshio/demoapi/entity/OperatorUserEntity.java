package com.example.toshio.demoapi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "operator_user")
@Data
public class OperatorUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String displayName;
}
