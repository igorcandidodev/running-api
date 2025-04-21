package com.runningapi.runningapi.security;

import com.runningapi.runningapi.exceptions.TokenInvalidException;
import com.runningapi.runningapi.service.user.UserDetailsSecurityService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FilterSecurity extends OncePerRequestFilter {

    @Autowired
    private JWTToken jwtToken;

    @Autowired
    private UserDetailsSecurityService userDetailsSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            var token = tokenRecovery(request);

            if(token == null) {
                filterChain.doFilter(request, response);
                return;
            }

            var email = jwtToken.validateToken(token);
            var userDetails = userDetailsSecurityService.loadUserByUsername(email);
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
        } catch (TokenInvalidException | UsernameNotFoundException e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
        }
    }

    private String tokenRecovery(HttpServletRequest request) {
        var token = request.getHeader("Authorization");

        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }

        return token.replace("Bearer ", "");
    }
}
