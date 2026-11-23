package com.example.TroEasyAPI.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String fullName;
    @Column(nullable = false, unique = true, length = 150)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
