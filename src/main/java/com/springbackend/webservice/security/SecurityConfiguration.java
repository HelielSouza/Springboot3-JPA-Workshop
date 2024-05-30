package com.springbackend.webservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserAuthenticationFilter userAuthenticationFilter;

    public static final String[] ENDPOINTS_GET = {
            "/users/**",
            "/orders/**",
            "/products/**",
            "/categories/**"
    };

    public static final String[] ENDPOINTS_POST_NO_AUTH = {
        "/users/login"
    };
        
    public static final String [] ENDPOINTS_WITH_AUTHENTICATION_REQUIRED = {
            "/users/test"
    };

    public static final String [] ENDPOINTS_CUSTOMER = {
            "/users/test/customer"
    };

    public static final String [] ENDPOINTS_ADMIN = {
            "/users/test/administrator"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable())
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, ENDPOINTS_GET).permitAll()
                .requestMatchers(HttpMethod.POST, ENDPOINTS_POST_NO_AUTH).permitAll()
                .requestMatchers(HttpMethod.POST, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
                .requestMatchers(ENDPOINTS_ADMIN).hasRole("ADMINISTRATOR") 
                .requestMatchers(ENDPOINTS_CUSTOMER).hasRole("CUSTOMER")
                .anyRequest().authenticated())
            .addFilterBefore(userAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
