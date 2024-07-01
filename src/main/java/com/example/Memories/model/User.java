package com.example.Memories.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
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
    private Long id;
    private String username;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private ImgurToken imgurToken;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Memory> memories;

    public User(String username, ImgurToken imgurToken, List<Memory> memories) {
        this.username = username;
        this.imgurToken = imgurToken;
        this.memories = memories;
        if (imgurToken != null) {
            imgurToken.setUser(this);
        }
    }

    @Transactional
    public void refreshImgurToken(ImgurToken token) {
        if (imgurToken != null) {
            imgurToken.setUser(null);
        }
        imgurToken = token;
        if (token != null) {
            token.setUser(this);
        }
    }
}
