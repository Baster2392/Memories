package com.example.Memories.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Memories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Memory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private String imgurAlbumId;
    private Date startDate;
    private Date endDate;
    @ManyToOne
    @JsonIgnore
    private User user;

    public Memory(String title, String description, String imgurAlbumId, Date startDate, Date endDate, User user) {
        this.title = title;
        this.description = description;
        this.imgurAlbumId = imgurAlbumId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
    }
}
