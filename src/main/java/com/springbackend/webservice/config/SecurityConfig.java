package com.springbackend.webservice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springbackend.webservice.security.jwt.JwtTokenFilter;
import com.springbackend.webservice.security.jwt.JwtTokenProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

	@Autowired
	private JwtTokenProvider tokenProvider;
	
	public static final String[] ENDPOINTS_GET = {
            "/users/**",
            "/orders/**",
            "/products/**",
            "/categories/**"
    };

	public static final String[] ENDPOINTS_PUBLIC = {
            "/h2-console/**",
            "/home/**"
    };
	
	@Bean
	PasswordEncoder passwordEncoder() {
		Map<String, PasswordEncoder> encoders = new HashMap<>();
				
		Pbkdf2PasswordEncoder pbkdf2Encoder = new Pbkdf2PasswordEncoder("", 8, 185000,
                SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256);
		encoders.put("pbkdf2", pbkdf2Encoder);
		DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder("pbkdf2", encoders);
		passwordEncoder.setDefaultPasswordEncoderForMatches(pbkdf2Encoder);
		return passwordEncoder;
	}
	
    @Bean
    AuthenticationManager authenticationManagerBean(
    		AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        JwtTokenFilter customFilter = new JwtTokenFilter(tokenProvider);
        
        //@formatter:off
        return http
            .httpBasic(basic -> basic.disable())
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions
                    .sameOrigin()
        	        ))
            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
            		session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                    authorizeHttpRequests -> authorizeHttpRequests
                    	.requestMatchers("/resources/**").permitAll()
                    	.requestMatchers(HttpMethod.GET, ENDPOINTS_GET).permitAll()
                    	.requestMatchers(ENDPOINTS_PUBLIC).permitAll()
                    	.requestMatchers(HttpMethod.POST, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
	                    .requestMatchers(HttpMethod.PUT, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
	                    .requestMatchers(HttpMethod.DELETE, "/users/**", "/orders/**", "/products/**", "/categories/**").authenticated()
	                    .requestMatchers("/auth/signin", "/auth/refresh/**").permitAll()
                )
            .cors(cors -> {})
                .build();
        //@formatter:on
    }
}