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
        // JWT 토큰 검증 및 인증 로직 구현
        // header or cookie에서 JWT 토큰을 가져와서 검증
        // 검증이 완료되면 SecurityContext에 Authentication 객체를 저장
        String headerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(headerToken) && headerToken.startsWith("Bearer ")) {
            String jwtToken = headerToken.substring(7);
            if (jwtTokenProvider.validateToken(jwtToken)) {
                // 토큰 검증이 완료되면 SecurityContext에 Authentication 객체를 저장
                String username = jwtTokenProvider.getUserId(jwtToken);

                 UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                 UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                 SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        String cookieToken = jwtCookieManager.getAccessTokenFromCookie(request);

        if (StringUtils.hasText(cookieToken)) {
            if (jwtTokenProvider.validateToken(cookieToken)) {
                String username = jwtTokenProvider.getUserId(cookieToken);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            SecurityContextHolder.clearContext();
        }






        filterChain.doFilter(request, response);
    }
}