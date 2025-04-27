package com.jv.backend.security;

import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWtFilter extends OncePerRequestFilter {

    @Autowired
    private JWtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        try {
            String header=request.getHeader("Authorization");
            if(header!=null && header.startsWith("Bearer ")) {
                String token=header.substring(7);

                if(!JWtUtil.isTokenExpired(token)) {
                    String username=JWtUtil.getUserName(token);
                    UserDetails user=userDetailsService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken credentials=new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext()
                            .setAuthentication(credentials);
                }

            }

        }catch(SignatureException e) {
            System.out.println("La signature n'est bonne");
        }
        filterChain.doFilter(request, response);

    }
}
