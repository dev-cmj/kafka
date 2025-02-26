package com.example.producer.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "security.jwt")
@Getter
@Setter
public class JwtProperties {
    private String secretKey;
    private AccessTokenProperties accessToken;
    private RefreshTokenProperties refreshToken;

    // Default values
    public JwtProperties() {
        this.accessToken = new AccessTokenProperties();
        this.refreshToken = new RefreshTokenProperties();

        this.accessToken.setExpiration(900); // 15 minutes
        this.accessToken.setCookie(CookieProperties.defaultCookie());

        this.refreshToken.setExpiration(604800); // 7 days
        this.refreshToken.setCookie(CookieProperties.defaultCookie());

    }

    @Getter
    @Setter
    public static class AccessTokenProperties {
        private long expiration;
        private CookieProperties cookie;
    }

    @Getter
    @Setter
    public static class RefreshTokenProperties {
        private long expiration;
        private CookieProperties cookie;
        // getters/setters
    }

    @Getter
    @Setter
    public static class CookieProperties {
        private String name;
        private String path;
        private String domain;
        private long maxAge;
        private boolean httpOnly;
        private boolean secure;

        private static CookieProperties defaultCookie() {
            CookieProperties cookie = new CookieProperties();
            cookie.name = "__sid";
            cookie.path = "/";
            cookie.domain = "";
            cookie.maxAge = 604800; // 7 days
            cookie.httpOnly = true;
            cookie.secure = false;
            return cookie;
        }
    }
}