package com.example.Memories.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    @OneToOne
    @JoinColumn(name = "credentials_user_id")
    private UserTokens userTokens;
    private List<Memory> memories;

    public User() {
    }

    public User(String username, UserTokens credentials, List<Memory> memories) {
        this.username = username;
        this.userTokens = credentials;
        this.memories = memories;
    }

    public UserTokens getUserTokens() {
        return userTokens;
    }

    public void setUserTokens(UserTokens userTokens) {
        this.userTokens = userTokens;
    }

    public String getUsername() {
        return username;
    }

    public List<Memory> getMemories() {
        return memories;
    }

    public void setMemories(List<Memory> memories) {
        this.memories = memories;
    }
}
