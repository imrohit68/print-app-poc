package com.example.PrintAppPOC.Security;

import com.example.PrintAppPOC.Exceptions.InvalidTokenException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull  HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        String requestToken = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if(requestToken==null || !requestToken.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        try {
            token = requestToken.substring(7);
            username = jwtTokenHelper.extractUsername(token);
            if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                if(jwtTokenHelper.validateToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                else {
                    System.out.println("Can't Validate");
                }
            }
            else {
                System.out.println("Username is null");
            }
            filterChain.doFilter(request,response);
        }catch (JwtException ex){
            System.out.println("hello world");
            System.out.println("------------");
            throw new InvalidTokenException("Authorization Failed");
        }

    }
}
