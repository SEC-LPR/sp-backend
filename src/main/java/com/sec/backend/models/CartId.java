package com.sec.backend.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CartId implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "product_id")
    private Long productId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartId cartId = (CartId) o;
        return Objects.equals(userId, cartId.userId) && Objects.equals(productId, cartId.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, productId);
    }
}
