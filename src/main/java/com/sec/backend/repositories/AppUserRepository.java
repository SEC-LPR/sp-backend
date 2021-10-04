package com.sec.backend.repositories;

import com.sec.backend.models.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@EnableJpaRepositories
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    Optional<AppUser> findByUsername(String username);

    @Modifying
    @Query("update AppUser u set u.creditCard = :creditCard where u.id = :id")
    int updateCreditCardById(@Param("id") Long id, @Param("creditCard") String creditCard);
}
