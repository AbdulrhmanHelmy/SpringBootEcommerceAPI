package com.helmy.ecommerce.Security;

import com.helmy.ecommerce.Util.JWTFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class FilterChain {

    private final JWTFilter jwtFilter;

    public FilterChain(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{

        return http.authorizeHttpRequests(

                        configure->configure
                                .requestMatchers("/cart", "/cart/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/auth/**").permitAll()
                                .requestMatchers("/favourite", "/favourite/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/orders/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/payment/**").hasAnyRole("USER","ADMIN")
                                .requestMatchers("/api/auth/verify").permitAll()
                                .requestMatchers("/api/cart", "/api/cart/**").permitAll()
                                .requestMatchers("/categories/admin/**").hasRole("ADMIN")
                                .requestMatchers("/product/admin/**").hasRole("ADMIN")
                                .requestMatchers("/categories/**").permitAll()
                                .requestMatchers("/product/**").permitAll()
                                .requestMatchers( "/v3/**","/swagger-ui/**").permitAll()
                                .requestMatchers( "/swagger-ui/**").permitAll()
                        )
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}