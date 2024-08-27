package com.javaproject.rasahotel.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.javaproject.rasahotel.constants.RoleConstant;
import com.javaproject.rasahotel.exception.CustomAccessDeniedException;
import com.javaproject.rasahotel.exception.CustomUnAuthorizeException;
import com.javaproject.rasahotel.security.JwtFilter;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

        @Bean
        PasswordEncoder getPasswordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Autowired
        JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(csrf -> csrf.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(new CustomUnAuthorizeException())
                                                .accessDeniedHandler(new CustomAccessDeniedException()))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers("/api/auth/register", "/api/auth/reset-password",
                                                                "/api/auth/forgot-password", "/api/room/all-room",
                                                                "/api/room/get-room", "/api/room/find-all",
                                                                "/api/auth/login", "/api/category/get-category",
                                                                "/api/category/all-category", "/api/category/get-sale",
                                                                "/api/category/all-sale", "/v3/api-docs/**",
                                                                "/swagger-ui/**")
                                                .permitAll()
                                                .requestMatchers("/api/room/update-room", "/api/room/update-room-photo",
                                                                "/api/room/add-room", "/api/room/delete-room",
                                                                "/api/category/delete-category",
                                                                "/api/category/add-category",
                                                                "/api/category/update-category",
                                                                "/api/category/delete-sale", "/api/category/add-sale",
                                                                "/api/category/update-sale",
                                                                "/api/report/report-daily",
                                                                "/api/report/report-monthly",
                                                                "/api/report/report-annual", "/api/report/report-pdf",
                                                                "/api/auth/delete-user")
                                                .hasAuthority(RoleConstant.ADMIN_ROLE)
                                                .requestMatchers("/api/booking/save-booking/**",
                                                                "/api/booking/get-by-email", "/api/booking/payment",
                                                                "/api/booking/payment-success",
                                                                "/api/booking/confirmation/**")
                                                .hasAuthority(RoleConstant.USER_ROLE)
                                                .requestMatchers("/api/customer/get-user",
                                                                "/api/booking/cancel-booking/**",
                                                                "/api/booking/all-booking")
                                                .hasAnyAuthority(RoleConstant.ADMIN_ROLE, RoleConstant.USER_ROLE)
                                                .anyRequest().authenticated())
                                .addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class);
                return http.build();
        }

}
