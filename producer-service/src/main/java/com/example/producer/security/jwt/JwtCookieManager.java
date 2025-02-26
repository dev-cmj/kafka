package com.example.producer.security.jwt;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class JwtCookieManager {
    private final JwtProperties jwtProperties;

    /**
     * Add Refresh Token to HttpOnly cookie
     */
    public void addRefreshTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(jwtProperties.getRefreshToken().getCookie().getName(), token);
        cookie.setDomain(jwtProperties.getRefreshToken().getCookie().getDomain());
        cookie.setHttpOnly(jwtProperties.getRefreshToken().getCookie().isHttpOnly());
        cookie.setSecure(jwtProperties.getRefreshToken().getCookie().isSecure());
        cookie.setPath(jwtProperties.getRefreshToken().getCookie().getPath());
        cookie.setMaxAge((int) jwtProperties.getRefreshToken().getCookie().getMaxAge());

        response.addCookie(cookie);
    }

    public void addAccessTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie(jwtProperties.getAccessToken().getCookie().getName(), token);
        cookie.setDomain(jwtProperties.getAccessToken().getCookie().getDomain());
        cookie.setHttpOnly(jwtProperties.getAccessToken().getCookie().isHttpOnly());
        cookie.setSecure(jwtProperties.getAccessToken().getCookie().isSecure());
        cookie.setPath(jwtProperties.getAccessToken().getCookie().getPath());
        cookie.setMaxAge((int) jwtProperties.getAccessToken().getCookie().getMaxAge());

        response.addCookie(cookie);
    }

    /**
     * Delete Refresh Token cookie
     */
    public void deleteRefreshTokenFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(jwtProperties.getRefreshToken().getCookie().getName(), "");
        cookie.setDomain(jwtProperties.getRefreshToken().getCookie().getDomain());
        cookie.setHttpOnly(jwtProperties.getRefreshToken().getCookie().isHttpOnly());
        cookie.setSecure(jwtProperties.getRefreshToken().getCookie().isSecure());
        cookie.setPath(jwtProperties.getRefreshToken().getCookie().getPath());
        cookie.setMaxAge(0); // Expire immediately

        response.addCookie(cookie);
    }

    public void deleteAccessTokenFromCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(jwtProperties.getAccessToken().getCookie().getName(), "");
        cookie.setDomain(jwtProperties.getAccessToken().getCookie().getDomain());
        cookie.setHttpOnly(jwtProperties.getAccessToken().getCookie().isHttpOnly());
        cookie.setSecure(jwtProperties.getAccessToken().getCookie().isSecure());
        cookie.setPath(jwtProperties.getAccessToken().getCookie().getPath());
        cookie.setMaxAge(0); // Expire immediately

        response.addCookie(cookie);
    }

    /**
     * Extract Refresh Token from request cookies
     */
    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (jwtProperties.getRefreshToken().getCookie().getName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public String getAccessTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (jwtProperties.getAccessToken().getCookie().getName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
