package com.sec.backend.controllers;

import com.sec.backend.dtos.OrderDto;
import com.sec.backend.models.OrderItem;
import com.sec.backend.services.OrderService;
import com.sec.backend.utils.DESUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final DESUtils desUtils;

    @PostMapping("{userId}/order/create")
    public ResponseEntity generateOrder(@PathVariable("userId") String userId,
                                        @RequestBody OrderDto orderDto) throws Exception {
        String encodeId = desUtils.decrypt(userId);

        Long id = Long.valueOf(encodeId);
        log.info("id, {}", id);

        Double temPrice = Double.valueOf(desUtils.decrypt(orderDto.getPrice()));
        BigDecimal totalPrice = BigDecimal.valueOf(temPrice);
        log.info("totalPrice, {}", totalPrice);

        Set<OrderItem> orderItemSet = orderDto.getList().stream()
                        .map((orderItemDto -> {
                            Double price = null;
                            try {
                                price = Double.valueOf(desUtils.decrypt(orderItemDto.getPrice()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            try {
                                return OrderItem.builder()
                                        .productId(Long.getLong(desUtils.decrypt(orderItemDto.getProductId())))
                                        .amount(Integer.getInteger(desUtils.decrypt(orderItemDto.getAmount())))
                                        .price(BigDecimal.valueOf(price))
                                        .build();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        })).collect(Collectors.toSet());

        orderService.createOrder(id, totalPrice, orderItemSet);
        return new ResponseEntity("Success", HttpStatus.OK);
    }
}
