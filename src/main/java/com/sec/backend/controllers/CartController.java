package com.sec.backend.controllers;

import com.sec.backend.dtos.CartGetDto;
import com.sec.backend.services.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity getCartInfo(@PathVariable("userId") String userId) {
        Long num = Long.valueOf(userId);
        List<CartGetDto> listOfCart = cartService.getListOfCart(num);
        return new ResponseEntity(listOfCart, HttpStatus.OK);
    };

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity removeItemFromCart(@PathVariable("userId") String userId,
                                             @PathVariable("productId") String productId) {
        Long userNum = Long.getLong(userId);
        Long productNum = Long.getLong(productId);
        cartService.removeFromCart(userNum, productNum);

        return new ResponseEntity("successful removal", HttpStatus.OK);
    }

    @PutMapping("/{userId}/{productId}")
    public ResponseEntity updateItemAmount(@PathVariable("userId") String userId,
                                           @PathVariable("productId") String productId,
                                           @RequestParam("amount") String amount) {
        Long userNum = Long.getLong(userId);
        Long productNum = Long.getLong(productId);
        Integer num = Integer.getInteger(amount);
        cartService.updateProductAmount(userNum, productNum, num);

        return new ResponseEntity("successful update", HttpStatus.OK);
    }

}
