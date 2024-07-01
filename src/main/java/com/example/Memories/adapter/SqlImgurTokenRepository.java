package com.example.Memories.adapter;

import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.User;
import com.example.Memories.model.repositories.ImgurTokenRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlImgurTokenRepository extends ImgurTokenRepository, JpaRepository<ImgurToken, User> {
}
