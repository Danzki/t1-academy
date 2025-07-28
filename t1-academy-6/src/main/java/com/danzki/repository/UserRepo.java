package com.danzki.repository;

import com.danzki.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    @Query(
            value = "SELECT * FROM ms_store_schema.user WHERE username = :username",
            nativeQuery = true
    )
    Optional<User> findByUsername(String username);
}
