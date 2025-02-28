package com.example.producer.auth;

import com.example.common.vo.LoginRequest;
import com.example.producer.security.jwt.JwtCookieManager;
import com.example.producer.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtCookieManager jwtCookieManager;

    public String login(LoginRequest loginRequest, HttpServletResponse response) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtTokenProvider.generateToken(authentication);
            String refreshToken = jwtTokenProvider.generateToken(authentication);

            jwtCookieManager.addAccessTokenToCookie(response, accessToken);
            jwtCookieManager.addRefreshTokenToCookie(response, refreshToken);

            return jwtTokenProvider.generateToken(authentication);

        } catch (AuthenticationException e) {
            throw new RuntimeException("Invalid username or password", e);
        }
    }
}