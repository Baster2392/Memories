package com.example.Memories.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Photos")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Photo {
    @Id
    private Integer id;
    private String url;
    private String description;
}
