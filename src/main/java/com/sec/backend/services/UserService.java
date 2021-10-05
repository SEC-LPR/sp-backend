package com.sec.backend.services;

import com.sec.backend.dtos.CreditCardDto;
import com.sec.backend.dtos.UserGetDto;
import com.sec.backend.dtos.UserPostDto;
import com.sec.backend.exceptions.UsernameConflictException;
import com.sec.backend.models.AppUser;
import com.sec.backend.models.CreditCard;
import com.sec.backend.repositories.AppUserRepository;
import com.sec.backend.utils.DESUtils;
import com.sec.backend.utils.RSAUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;
    private final DESUtils desUtils;

    String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALCMObUdcPDxAflm0YxAiXaH8jwT5yE5ADxpDzEJH/5oedE5o39lTlWZ3ZnPuDpwBpxH0FKbrT6JIJi28QYkqXRaq9s8YmRzy152M0XVCqBaqSS4TpR2DDY6QQokLEODo+sCeJHsJzSKj0bxtbg/wkMmNJttp+8w8MMVtVVRYnHHAgMBAAECgYAOLuW/8CKPqL0A3Uq+WrzwYdGLFApAeATV1Zbb2KDSXnBS56+D346gf+D2p2Jkh3VwfrB0wn7zhC6zNhc86BsY1K6Q7xU8b7asDBqki48H3ExuxjEosEqUdLf9p9pPBCPI3I4CN/EZPEoFjNRNi5ZX/CY4iaOgsXPHEkcxuW3GQQJBAOBo9UpXesZYCsmuuGN5SOy6tXI613NUBvXKXkxBil3ZOqozlaSjv5NSQb2zLeh2DcYfecumfgn04rNM/pLeDmECQQDJZnXWg4VrXdjc9hqu/8rkmxdhsr3ERkX1uKJrDUB+AOdFs6mS9gEHnJ70zqQ2ucbhQ4htulbLc9pBVL5gy+EnAkEArdhhfa/7SsBVyxvxeA4zMkEJ4242Df/gTHTzTDvRxxZL3iKMILlB5gzpJN40CEu8K+miXuOh7HCrVp+k733awQJBAMDkERhS/wXF7F40l3nkIz6wC8TWnEnPxFGDdItzNcF4vAhV+qN2WaYgq11sTHrdk01MkO4G+foCC5dmwq+SlSECQGm58ReqUTRDAKl32VX0vEdVvOj2getVxW6jQjJFiGj8iNDfK+DpiLfns3YjwSlWHGxHz1S6/lQ+58+M+fEyvUs=";

    public UserGetDto login(String username, String password) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
        String encodedPassword = passwordEncoder.encode(password);

        Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, password));

        return mappingToGetDto(appUser);

    }

    public UserGetDto createUser(UserPostDto userPostDto) {
        if(appUserRepository.findByUsername(userPostDto.getUsername()).isPresent()) {
            throw new UsernameConflictException("This username has been used.");
        }
        AppUser appUser = mappingToAppUser(userPostDto);
        appUser.setPassword(passwordEncoder.encode(userPostDto.getPassword()));
        appUserRepository.save(appUser);
        return mappingToGetDto(appUser);
    }

    public void setCreditCard(Long id, CreditCardDto creditCardDto) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("No such client!"));
        CreditCard creditCard = mappingToCreditCard(creditCardDto);

        user.getCreditCards().add(creditCard);
        appUserRepository.save(user);

    }

//    public void checkCreditCard(Long id) {
//        AppUser appUser = appUserRepository.findById(id)
//                .orElseThrow(() -> new UsernameNotFoundException("No such user"));
//
//        if( appUser.getCreditCard() == null ) {
//            throw new CreditCardNotFoundException("This user has not set credit card.");
//        }
//    }

    private UserGetDto mappingToGetDto(AppUser appUser) {
        return UserGetDto.builder()
                .id(appUser.getId())
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .build();
    }

    private AppUser mappingToAppUser(UserPostDto userPostDto) {
        return AppUser.builder()
                .username(userPostDto.getUsername().toLowerCase(Locale.ROOT))
                .firstName(userPostDto.getFirstName())
                .lastName(userPostDto.getLastName())
                .build();
    }

    private CreditCard mappingToCreditCard(CreditCardDto creditCardDto) {
        return CreditCard.builder()
                .cardName(creditCardDto.getCardName())
                .cardNumber(creditCardDto.getCardNumber())
                .expDate(creditCardDto.getExpDate())
                .cvv(creditCardDto.getCvv())
                .build();
    }

    public String decode(String code) throws Exception {
        byte[] decodedData = RSAUtils.decryptByPrivateKey(decryptBASE64(code),
                        privateKey);
        return new String(decodedData);
    }
    public static byte[] decryptBASE64(String key) {
        return Base64.getDecoder().decode(key);
    }

    public String decodeDES(String code) throws Exception {
        return desUtils.decrypt(code);
    }
}
