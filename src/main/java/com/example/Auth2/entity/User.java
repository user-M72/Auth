package com.example.Auth2.entity;

import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.Id;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username; // Можно хранить Google name или свой логин

    @Column(unique = true, nullable = false)
    private String email;

    private String password; // при OAuth можно оставлять null

}
