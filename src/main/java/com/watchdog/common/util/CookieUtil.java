package com.watchdog.common.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CookieUtil {
    public ResponseCookie createCookie(String tokenName, String token, long expirationTime) {
        return ResponseCookie.from(tokenName, token)
                .domain("watchdog.site")
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(expirationTime / 1000)
                .build();
    }

    public String getCookieValue(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public ResponseCookie deleteCookie(String cookieName) {
        return ResponseCookie.from(cookieName, "")
                .domain("watchdog.site")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();
    }
}
