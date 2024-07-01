package com.example.Memories.adapter;

import com.example.Memories.model.User;
import com.example.Memories.model.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface SqlUserRepository extends UserRepository, JpaRepository<User, Long> {
}
