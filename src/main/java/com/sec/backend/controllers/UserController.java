package com.sec.backend.controllers;

import com.sec.backend.dtos.*;
import com.sec.backend.models.RSAPublicKey;
import com.sec.backend.models.Rsa;
import com.sec.backend.services.UserService;
import com.sec.backend.utils.DESUtils;
import com.sec.backend.utils.RSAUtils;
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
    private final RSAUtils RSAUtils;
    private final DESUtils desUtils;
    private final RSAPublicKey rsaPublicKey;
    String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALCMObUdcPDxAflm0YxAiXaH8jwT5yE5ADxpDzEJH/5oedE5o39lTlWZ3ZnPuDpwBpxH0FKbrT6JIJi28QYkqXRaq9s8YmRzy152M0XVCqBaqSS4TpR2DDY6QQokLEODo+sCeJHsJzSKj0bxtbg/wkMmNJttp+8w8MMVtVVRYnHHAgMBAAECgYAOLuW/8CKPqL0A3Uq+WrzwYdGLFApAeATV1Zbb2KDSXnBS56+D346gf+D2p2Jkh3VwfrB0wn7zhC6zNhc86BsY1K6Q7xU8b7asDBqki48H3ExuxjEosEqUdLf9p9pPBCPI3I4CN/EZPEoFjNRNi5ZX/CY4iaOgsXPHEkcxuW3GQQJBAOBo9UpXesZYCsmuuGN5SOy6tXI613NUBvXKXkxBil3ZOqozlaSjv5NSQb2zLeh2DcYfecumfgn04rNM/pLeDmECQQDJZnXWg4VrXdjc9hqu/8rkmxdhsr3ERkX1uKJrDUB+AOdFs6mS9gEHnJ70zqQ2ucbhQ4htulbLc9pBVL5gy+EnAkEArdhhfa/7SsBVyxvxeA4zMkEJ4242Df/gTHTzTDvRxxZL3iKMILlB5gzpJN40CEu8K+miXuOh7HCrVp+k733awQJBAMDkERhS/wXF7F40l3nkIz6wC8TWnEnPxFGDdItzNcF4vAhV+qN2WaYgq11sTHrdk01MkO4G+foCC5dmwq+SlSECQGm58ReqUTRDAKl32VX0vEdVvOj2getVxW6jQjJFiGj8iNDfK+DpiLfns3YjwSlWHGxHz1S6/lQ+58+M+fEyvUs=";


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

    @PostMapping("/{userId}/credit")
    public ResponseEntity addCreditCard(@PathVariable("userId") String id,
                                        @RequestBody CreditCardDto creditCardDto) throws Exception {
        Long userId = Long.valueOf(desUtils.decrypt(id));
        log.info("add credit: {}", userId);
        log.info("add cardName: {}", desUtils.decrypt(creditCardDto.getCardName()));
        log.info("add cardNumber: {}", desUtils.decrypt(creditCardDto.getCardNumber()));
        log.info("add expDate: {}", desUtils.decrypt(creditCardDto.getExpDate()));
        log.info("add cvv: {}", desUtils.decrypt(creditCardDto.getCvv()));

        CreditCardDto savedCardInfo = CreditCardDto.builder()
                .cardName(desUtils.decrypt(creditCardDto.getCardName()))
                .cardNumber(desUtils.decrypt(creditCardDto.getCardNumber()))
                .expDate(desUtils.decrypt(creditCardDto.getExpDate()))
                .cvv(desUtils.decrypt(creditCardDto.getCvv()))
                .build();
        userService.setCreditCard(userId, savedCardInfo);

        return new ResponseEntity("successful update", HttpStatus.OK);

    }

    @GetMapping("/rsa")
    public ResponseEntity returnRSA() {

        return new ResponseEntity(new Rsa().getPublicKey(), HttpStatus.OK);
    }

    @PostMapping("/des")
    public ResponseEntity getDES(@RequestBody DesPostDto desPostDto ) throws Exception {
        String code = desPostDto.getKey();
        String encode = userService.decode(code);
            log.info("key: {}", encode);
            rsaPublicKey.setRsaPublicKey(encode);
        return new ResponseEntity("get key", HttpStatus.OK);
    }

//    @GetMapping("/{userId}/credit")
//    public ResponseEntity checkCreditCard(@PathVariable Long id) {
//
//        userService.checkCreditCard(id);
//
//        return new ResponseEntity("Credit card added", HttpStatus.OK);
//    }

    @GetMapping("/test")
    public ResponseEntity test(@RequestBody CodeDto code) throws Exception {
        log.info("code" + code);
        String originText = userService.decodeDES(code.getCode());
        return new ResponseEntity(originText, HttpStatus.OK);
    }

}
