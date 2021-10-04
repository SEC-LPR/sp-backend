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
    public ResponseEntity getCartInfo(@PathVariable("userId") Long userId) {
        List<CartGetDto> listOfCart = cartService.getListOfCart(userId);
        return new ResponseEntity(listOfCart, HttpStatus.OK);
    };

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity removeItemFromCart(@PathVariable("userId") Long userId,
                                             @PathVariable("productId") Long productId) {
        cartService.removeFromCart(userId, productId);

        return new ResponseEntity("successful removal", HttpStatus.OK);
    }

    @PutMapping("/{userId}/{productId}")
    public ResponseEntity updateItemAmount(@PathVariable("userId") Long userId,
                                           @PathVariable("productId") Long productId,
                                           @RequestParam("amount") Integer amount) {
        cartService.updateProductAmount(userId, productId, amount);

        return new ResponseEntity("successful update", HttpStatus.OK);
    }

}
