package com.danzki.repository;

import com.danzki.model.Limit;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LimitRepository extends JpaRepository<Limit, Long> {
    Optional<Limit> findByClientId(Long clientId);

    @Modifying
    @Transactional
    @Query(
            value = "UPDATE ms_store_schema.limits SET current_amount = :amount",
            nativeQuery = true
    )
    int updateAllCurrentAmount(@Param("amount") Double amount);

    @Modifying
    @Transactional
    @Query(
            value = "DELETE FROM ms_store_schema.limits WHERE client_id = :clientId",
            nativeQuery = true
    )
    int deleteByClientId(@Param("clientId") Long clientId);
}
