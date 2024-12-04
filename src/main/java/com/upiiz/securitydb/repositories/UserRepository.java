package com.upiiz.securitydb.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.upiiz.securitydb.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // Entiende - Query Methods
    Optional<UserEntity> findUserEntityByUsername(String username);
}
