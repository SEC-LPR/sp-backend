package com.sec.backend.auth;

import com.sec.backend.repositories.AppUserRepository;
import com.sec.backend.models.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("No user with %s", username)));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("role:user");
        List<GrantedAuthority> authorities = List.of(authority);

        return new User(appUser.getUsername(),
                appUser.getPassword().replaceAll("\\s+", ""),
                authorities);
    }
}
