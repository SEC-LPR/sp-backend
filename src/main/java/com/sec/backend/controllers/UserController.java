package com.sec.backend.controllers;

import com.sec.backend.dtos.UserGetDto;
import com.sec.backend.dtos.UserLoginDto;
import com.sec.backend.dtos.UserPostDto;
import com.sec.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto) {

        UserGetDto userGetDto = userService.login(userLoginDto.getUsername().toLowerCase(Locale.ROOT),
                userLoginDto.getPassword());

        return new ResponseEntity(userGetDto, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity signup(@RequestBody UserPostDto userPostDto) {

        UserGetDto userGetDto = userService.createUser(userPostDto);

        return new ResponseEntity(userGetDto, HttpStatus.OK);

    }

    @PutMapping("/{userId}/credit")
    public ResponseEntity addCreditCard(@PathVariable Long id,
                                        @RequestParam(value = "credit") String creditCard) {
        userService.setCreditCard(id, creditCard);

        return new ResponseEntity("successful update", HttpStatus.OK);

    }

    @GetMapping("/{userId}/credit")
    public ResponseEntity checkCreditCard(@PathVariable Long id) {

        userService.checkCreditCard(id);

        return new ResponseEntity("Credit card added", HttpStatus.OK);
    }
}
