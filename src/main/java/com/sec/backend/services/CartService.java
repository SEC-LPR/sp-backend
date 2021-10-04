package com.sec.backend.services;

import com.sec.backend.dtos.CartGetDto;
import com.sec.backend.models.Cart;
import com.sec.backend.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    public List<CartGetDto> getListOfCart(Long userId) {
        List<Cart> cartList = cartRepository.findProductByUserId(userId);

        if(cartList.size() == 0) {
            return new ArrayList<>();
        }
        return cartList.stream().map(cart -> mappingToGetDto(cart)).collect(Collectors.toList());
    }

    @Transactional
    public void removeFromCart(Long userId, Long productId) {
        int num = cartRepository.deleteCart(userId, productId);
    }

    @Transactional
    public void updateProductAmount(Long userId, Long productId, Integer amount) {
        int num = cartRepository.updateProductAmount(amount, userId, productId);
    }

    private CartGetDto mappingToGetDto(Cart cart) {
        return CartGetDto.builder()
                .productId(cart.getId().getProductId())
                .productName(cart.getProduct().getName())
                .amount(cart.getAmount())
                .price(cart.getProduct().getPrice())
                .build();
    }

}
