package com.example.Memories.model.repositories;

import com.example.Memories.model.ImgurToken;
import com.example.Memories.model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User save(User user);
    List<User> findByName(String username);
    List<User> findByEmail(String email);
    void deleteById(Long id);


    @Modifying
    @Transactional
    @Query("UPDATE User u set u.imgurToken = :imgurToken WHERE u.id = :id")
    void updateImgurToken(@Param("id") Long id, @Param("imgurToken") ImgurToken imgurToken);
}
