package com.example.Memories.model.repositories;

import com.example.Memories.model.Photo;

import java.util.List;

public interface PhotoRepository {
    List<Photo> findAll();
    Photo save(Photo photo);
}
