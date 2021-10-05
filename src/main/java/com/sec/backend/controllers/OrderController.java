package com.sec.backend.controllers;

import com.sec.backend.dtos.OrderDto;
import com.sec.backend.models.OrderItem;
import com.sec.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("{userId}/order/create")
    public ResponseEntity generateOrder(@PathVariable String userId,
                                        @RequestBody OrderDto orderDto) {
        Long id = Long.getLong(userId);
        Double temPrice = Double.valueOf(orderDto.getPrice());
        BigDecimal totalPrice = BigDecimal.valueOf(temPrice);
        Set<OrderItem> orderItemSet = orderDto.getList().stream()
                        .map((orderItemDto -> {
                            Double price = Double.valueOf(orderItemDto.getPrice());
                            return OrderItem.builder()
                                    .productId(Long.getLong(orderItemDto.getProductId()))
                                    .amount(Integer.getInteger(orderItemDto.getAmount()))
                                    .price(BigDecimal.valueOf(price))
                                    .build();
                        })).collect(Collectors.toSet());

        orderService.createOrder(id, totalPrice, orderItemSet);
        return new ResponseEntity("Success", HttpStatus.OK);
    }
}
