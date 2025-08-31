package com.danzki.repository;

import com.danzki.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.Optional;

@Repository
public interface UserRepo extends ReactiveCrudRepository<User, Long> {
    @Query(
            value = "SELECT * FROM ms_store_schema.user WHERE username = :username",
            nativeQuery = true
    )
    Flux<Optional<User>> findByUsername(String username);

    Flux<User> findAll();
}
