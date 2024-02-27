package com.example.Memories.model;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "Memories")
public class Memory {
    @Id
    private Integer id;
    private String title;
    private String description;
}
