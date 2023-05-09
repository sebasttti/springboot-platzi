package com.unbosque.edu.co.market.web.security;

import com.unbosque.edu.co.market.domain.service.MarketUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig{
    @Autowired
    MarketUserDetailsService marketUserDetailsService;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {

        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(marketUserDetailsService)
                .and()
                .build();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/**/authenticate").hasAnyRole()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/authenticate")
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll());

        return http.build();
    }
}
