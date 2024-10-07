package com.example.Memories.model;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImgurToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    private User user;
    private String imgurName;
    private String accessToken;
    private String refreshToken;
    private Long expiresIn;

    public ImgurToken(String accessToken, String refreshToken, Long expiresIn, String imgurName) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.imgurName = imgurName;
    }

    public void setUser(User user) {
        this.user = user;
        this.tokenId = user != null ? user.getId() : null;
    }
}
