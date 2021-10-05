package com.sec.backend.services;

import com.sec.backend.models.Order;
import com.sec.backend.models.OrderItem;
import com.sec.backend.repositories.CartRepository;
import com.sec.backend.repositories.OrderItemRepository;
import com.sec.backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;

    @Transactional
    public void createOrder(Long userId, BigDecimal price, Set<OrderItem> items) {
        Order order = Order.builder()
                .userId(userId)
                .orderItems(items)
                .totalPrice(price)
                .createdTime(OffsetDateTime.now())
                .build();
        orderRepository.save(order);

        cartRepository.deleteCartByUserId(userId);

    }
}
