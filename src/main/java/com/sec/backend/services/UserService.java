package com.sec.backend.services;

import com.sec.backend.dtos.UserGetDto;
import com.sec.backend.dtos.UserPostDto;
import com.sec.backend.exceptions.CreditCardNotFoundException;
import com.sec.backend.exceptions.UsernameConflictException;
import com.sec.backend.models.AppUser;
import com.sec.backend.repositories.AppUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final AppUserRepository appUserRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserGetDto login(String username, String password) {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));

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

    public void setCreditCard(Long id, String creditCard) {
        appUserRepository.updateCreditCardById(id, creditCard);
    }

    public void checkCreditCard(Long id) {
        AppUser appUser = appUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("No such user"));

        if( appUser.getCreditCard() == null ) {
            throw new CreditCardNotFoundException("This user has not set credit card.");
        }
    }

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
                .username(userPostDto.getUsername())
                .firstName(userPostDto.getFirstName())
                .lastName(userPostDto.getLastName())
                .build();
    }
}
