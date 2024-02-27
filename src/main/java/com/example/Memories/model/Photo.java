package com.example.Memories.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "Photos")
public class Photo {
    @Id
    private Integer id;
    private String url;
    private String description;
    private Date date;
}
