package com.example.Memories.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @OneToOne
    private UserTokens userTokens;
    @OneToMany
    private List<Memory> memories;

    public User(String username, UserTokens credentials, List<Memory> memories) {
        this.username = username;
        this.userTokens = credentials;
        this.memories = memories;
    }
}
