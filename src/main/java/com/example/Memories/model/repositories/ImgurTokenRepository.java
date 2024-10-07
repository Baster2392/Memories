package com.example.Memories.model.repositories;

import com.example.Memories.model.ImgurToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ImgurTokenRepository {
    ImgurToken save(ImgurToken imgurToken);
    void deleteById(Long id);
}
