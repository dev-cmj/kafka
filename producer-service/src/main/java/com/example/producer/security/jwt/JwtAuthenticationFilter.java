package com.example.producer.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtCookieManager jwtCookieManager;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Try to authenticate using token from different sources in order of priority
        boolean authenticated = authenticateWithHeaderToken(request) ||
                authenticateWithAccessCookie(request) ||
                authenticateWithRefreshCookie(request);

        if (!authenticated) {
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Attempt authentication using the Authorization header Bearer token
     */
    private boolean authenticateWithHeaderToken(HttpServletRequest request) {
        String headerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
            String jwtToken = headerToken.substring(7);
            return authenticateWithToken(jwtToken);
        }

        return false;
    }

    /**
     * Attempt authentication using the access token from cookies
     */
    private boolean authenticateWithAccessCookie(HttpServletRequest request) {
        String cookieToken = jwtCookieManager.getAccessTokenFromCookie(request);

        if (StringUtils.hasText(cookieToken)) {
            return authenticateWithToken(cookieToken);
        }

        return false;
    }

    /**
     * Attempt authentication using the refresh token from cookies
     */
    private boolean authenticateWithRefreshCookie(HttpServletRequest request) {
        String refreshToken = jwtCookieManager.getRefreshTokenFromCookie(request);

        if (StringUtils.hasText(refreshToken)) {
            return authenticateWithToken(refreshToken);
        }

        return false;
    }

    /**
     * Common method to authenticate with a token
     */
    private boolean authenticateWithToken(String token) {
        if (jwtTokenProvider.validateToken(token)) {
            String username = jwtTokenProvider.getUserId(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        }

        return false;
    }

    }