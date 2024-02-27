package com.example.Memories.adapter;

import com.example.Memories.model.Photo;
import com.example.Memories.model.PhotoRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlPhotoRepository extends PhotoRepository, JpaRepository<Photo, Integer> {
}
