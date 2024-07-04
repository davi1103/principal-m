package com.arqui.GreenCoom.Authentication.Config;

import com.arqui.GreenCoom.Authentication.jwt.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtService jwtService;
    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService) {

        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String tokenFromRequest = request.getHeader("Authorization");
        System.out.println("Token from request" + tokenFromRequest);
        String userName=null;
        String jwtToken=null;
        logger.debug("Inside JwtRequestFilter--OncePerRequestFilter");
        System.out.println(tokenFromRequest);
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (tokenFromRequest != null && tokenFromRequest.startsWith("Bearer ")) {

            jwtToken = tokenFromRequest.substring(7);
            logger.info("JWT Token: {}");
            System.out.println(jwtToken);
            try {
                userName = jwtService.getUserNameFromToken(jwtToken);
                System.out.println(userName);
            } catch (IllegalArgumentException e) {
                logger.error("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token has expired");
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String");
        }

        //  Once we get the token validate it and extract username(principal/subject) from it.
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            System.out.println(userName);
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(userName);
            System.out.println(userDetails.getUsername() + "4");

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtService.validateToken(jwtToken, userDetails)) {

                System.out.println(userDetails.getAuthorities() + "5");
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                System.out.println(userDetails.getUsername() + "6");
            }
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        System.out.println("Checking if should not filter path: " + path);
        return path.equals("/user/login") || path.equals("/user/create");
    }

}
