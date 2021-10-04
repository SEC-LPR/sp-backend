package com.sec.backend.repositories;

import com.sec.backend.models.Cart;
import com.sec.backend.models.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface CartRepository extends JpaRepository<Cart, CartId> {

    @Query("select c from Cart c where c.user.id = :userId")
    List<Cart> findProductByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("delete from Cart c where c.id = :cartId")
    int deleteByCartId(@Param("cardId") CartId cartId);

    @Modifying
    @Query("update Cart c set c.amount = :amount where c.id = :cartId")
    int updateProductAmount(@Param("amount") Integer amount, @Param("cartId") CartId cartId);
}
