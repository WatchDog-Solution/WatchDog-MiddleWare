package com.watchdog.common.auth;

import com.watchdog.common.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenCookieManager {
    private final CookieUtil cookieUtil;

    @Value("${jwt.access-token.expiration-time}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refresh-token.expiration-time}")
    private long refreshTokenExpirationTime;

    public void addAccessTokenCookie(HttpServletResponse response, String accessToken) {
        ResponseCookie accessTokenCookie = cookieUtil.createCookie("accessToken", accessToken, accessTokenExpirationTime);
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
    }

    public void addRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie refreshTokenCookie = cookieUtil.createCookie("refreshToken", refreshToken, refreshTokenExpirationTime);
    }

    public void removeRefreshTokenCookie(HttpServletResponse response) {
        ResponseCookie refreshTokenCookie = cookieUtil.deleteCookie("refreshToken");
        response.addHeader("Set-Cookie", refreshTokenCookie.toString());
    }

    public void removeAccessTokenCookie(HttpServletResponse response) {
        ResponseCookie accessTokenCookie = cookieUtil.deleteCookie("accessToken");
        response.addHeader("Set-Cookie", accessTokenCookie.toString());
    }
}
