package com.doubleat.ccgame.security;

import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Utility class for Spring Security.
 *
 * @author Hop Nguyen
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Create authentication cookie with an access token and expire time.
     *
     * @param accessToken                       the token need to create cookie.
     * @param accessTokenValidityInMilliseconds expire time of the cookie.
     * @return {@code HttpCookie}.
     */
    public static HttpCookie createAuthCookie(String accessToken, Long accessTokenValidityInMilliseconds) {
        return ResponseCookie
                .from(SecurityConstants.ACCESS_TOKEN_COOKIE, accessToken)
                .httpOnly(true)
                .secure(false)
                //                .sameSite("")
                .maxAge(accessTokenValidityInMilliseconds)
                .path("/")
                .build();
    }

    /**
     * Logout
     */
    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
    }

    /**
     * Get the JWT of the current user.
     *
     * @return the JWT of the current user.
     */
    public static String getCurrentAccessToken() {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getCredentials() instanceof String) {
                return (String) authentication.getCredentials();

            }
        }

        return null;
    }

    /**
     * Get the JWT of the request
     *
     * @param request the request need to get token form this.
     * @return JWT token from the request.
     */
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = null;
        String authentication = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(authentication) && authentication.startsWith("Bearer ")) {
            bearerToken = authentication.substring(7);
        }

        if (bearerToken == null) {
            bearerToken = getTokenFromCookie(request.getCookies());
        }

        return bearerToken;
    }

    private static String getTokenFromCookie(Cookie... cookies) {
        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (SecurityConstants.ACCESS_TOKEN_COOKIE.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }

        return null;
    }

}
