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
    @Query("delete from Cart c where c.user.id = :userId and c.product.id = :productId")
    int deleteCart(@Param("userId") Long userId, @Param("productId") Long productId);

    @Modifying
    @Query("delete from Cart c where c.user.id = :userId")
    int deleteCartByUserId(@Param("userId") Long userId);

    @Modifying
    @Query("update Cart c set c.amount = :amount where c.user.id = :userId and c.product.id = :productId")
    int updateProductAmount(@Param("amount") Integer amount,
                            @Param("userId") Long userId,
                            @Param("productId") Long productId);


}
