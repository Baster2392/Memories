package com.example.Memories.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private User user;
    private String accessToken;
    private String refreshToken;
    private Integer expiresIn;

    public ImgurToken(String accessToken, String refreshToken, Integer expiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
    }

    public void setUser(User user) {
        this.user = user;
        this.tokenId = user != null ? user.getId() : null;
    }
}
