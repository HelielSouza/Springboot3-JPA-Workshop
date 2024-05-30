package com.springbackend.webservice.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import com.springbackend.webservice.entities.User;
import com.springbackend.webservice.entities.implementations.UserDetailsImpl;
import com.springbackend.webservice.repositories.UserRepository;
import com.springbackend.webservice.services.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//UserAuthenticationFilter.java
@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

 @Autowired
 private JwtTokenService jwtTokenService;

 @Autowired
 private UserRepository userRepository;

 private AntPathMatcher antPathMatcher = new AntPathMatcher();

 @Override
 protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     if (checkIfEndpointIsNotPublic(request)) {
         String token = recoveryToken(request);
         if (token != null) {
             String subject = jwtTokenService.getSubjectFromToken(token);
             User user = userRepository.findByEmail(subject).orElse(null);
             if (user != null) {
                 UserDetailsImpl userDetails = new UserDetailsImpl(user);
                 Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                 SecurityContextHolder.getContext().setAuthentication(authentication);
             }
         } else {
             response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
             response.getWriter().write("The token is absent");
             return;
         }
     }
     filterChain.doFilter(request, response);
 }

 private String recoveryToken(HttpServletRequest request) {
     String authorizationHeader = request.getHeader("Authorization");
     if (authorizationHeader != null) {
         return authorizationHeader.replace("Bearer ", "");
     }
     return null;
 }

 private boolean checkIfEndpointIsNotPublic(HttpServletRequest request) {
     String requestURI = request.getRequestURI();
     for (String endpoint : SecurityConfiguration.ENDPOINTS_GET) {
         if (antPathMatcher.match(endpoint, requestURI)) {
             return false;
         }
     }
     return true;
 }
}
