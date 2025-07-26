package com.danzki.repository;

import com.danzki.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProductRepo extends JpaRepository<UserProduct, Long> {

    @Query(
        value = "SELECT * FROM user_product WHERE user_id = :userId",
        nativeQuery = true)
    List<UserProduct> findAllByUser(Long userId);

    @Override
    @Query(
            value = "SELECT * FROM user_product WHERE id = :productId",
            nativeQuery = true)
    Optional<UserProduct> findById(Long productId);
}
