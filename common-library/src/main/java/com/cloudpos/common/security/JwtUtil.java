package com.cloudpos.common.security;

import com.cloudpos.common.exception.UnauthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.DecodingException;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Stateless JWT helper shared by services that issue or validate Cloud POS tokens.
 */
public final class JwtUtil {

    public static final String ROLE_CLAIM = "role";
    public static final String USER_ID_CLAIM = "userId";
    public static final String TENANT_ID_CLAIM = "tenantId";

    private JwtUtil() {
    }

    public static String generateToken(AuthenticatedUser user, String secret, long expirationMs) {
        return generateToken(user.getEmail(), user.getRole().name(), user.getUserId(), user.getTenantId(), secret, expirationMs);
    }

    public static String generateToken(String email, String role, String userId, String tenantId, String secret, long expirationMs) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        Map<String, Object> claims = new HashMap<>();
        claims.put(ROLE_CLAIM, role);
        claims.put(USER_ID_CLAIM, userId);
        claims.put(TENANT_ID_CLAIM, tenantId);

        return Jwts.builder()
                .claims(claims)
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .signWith(signingKey(secret))
                .compact();
    }

    public static boolean validateToken(String token, String secret) {
        parseClaims(token, secret);
        return true;
    }

    public static String extractEmail(String token, String secret) {
        return parseClaims(token, secret).getSubject();
    }

    public static String extractRole(String token, String secret) {
        return parseClaims(token, secret).get(ROLE_CLAIM, String.class);
    }

    public static String extractUserId(String token, String secret) {
        return parseClaims(token, secret).get(USER_ID_CLAIM, String.class);
    }

    public static String extractTenantId(String token, String secret) {
        return parseClaims(token, secret).get(TENANT_ID_CLAIM, String.class);
    }

    public static Date extractExpiration(String token, String secret) {
        return parseClaims(token, secret).getExpiration();
    }

    private static Claims parseClaims(String token, String secret) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey(secret))
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (JwtException | IllegalArgumentException ex) {
            throw new UnauthorizedException("Invalid or expired JWT token");
        }
    }

    private static SecretKey signingKey(String secret) {
        try {
            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        } catch (DecodingException ex) {
            return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        }
    }
}
