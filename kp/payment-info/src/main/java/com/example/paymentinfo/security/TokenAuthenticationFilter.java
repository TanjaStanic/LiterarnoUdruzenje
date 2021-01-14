package com.example.paymentinfo.security;


import com.example.paymentinfo.service.CustomUserDetailsService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {
    private TokenUtils tokenUtils;
    private CustomUserDetailsService customUserDetailsService;

    public TokenAuthenticationFilter(TokenUtils tokenHelper, CustomUserDetailsService customUserDetailsService){
        this.tokenUtils = tokenHelper;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String username;
        String authToken = tokenUtils.getToken(request);

        if(authToken != null){
            username = tokenUtils.getClaims(authToken).getSubject();
            if(username != null){
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                if(tokenUtils.validateToken(authToken, userDetails)){
                    TokenBasedAuthentication authentication = new TokenBasedAuthentication(userDetails);
                    authentication.setToken(authToken);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        chain.doFilter(request, response);
    }
}
