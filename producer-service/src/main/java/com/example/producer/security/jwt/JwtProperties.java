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

    // Default values
    public JwtProperties() {
        this.accessToken = new AccessTokenProperties();
        this.accessToken.setExpiration(900);
    }

    @Getter
    @Setter
    public static class AccessTokenProperties {
        private long expiration;  // seconds
        private String issuer;
        private String audience;
    }
}