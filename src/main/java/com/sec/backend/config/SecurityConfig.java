package com.sec.backend.config;

import com.sec.backend.auth.ApplicationUserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ApplicationUserService applicationUserService;

    @Override
    @SneakyThrows
    protected void configure(HttpSecurity http) {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors().configurationSource(request -> {
                     var cors = new CorsConfiguration();
                     cors.setAllowedOrigins(List.of(
                             "http://localhost:3000"
                     ));
                     cors.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
                     cors.setAllowedHeaders(List.of("*"));
                     return cors;
                })
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/login", "signup", "/{userId}/credit",
                        "/cart/{userId}/{productId}", "/cart/{userId}"
                )
                .permitAll()
                .antMatchers("/", "index", "/css/*", "/actuator/*")
                .permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    @SneakyThrows
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
}
