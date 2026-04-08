package owl.medassist_back.userService.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import owl.medassist_back.userService.dto.AuthResponseDto;
import owl.medassist_back.userService.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.user-service.secret-key}")
    private String secretKey;

    @Value("${spring.user-service.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${spring.user-service.refresh-token-expiration}")
    private long refreshTokenExpiration;

    public AuthResponseDto generateTokens(User user) {

        return new AuthResponseDto(
                generateAccessToken(user),
                generateRefreshToken(user),
                (int) accessTokenExpiration,
                (int) refreshTokenExpiration
        );
    }

    private String generateAccessToken(User user) {

        return Jwts.builder()
                .subject(user.getLogin())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(getSignKey())
                .compact();
    }

    private String generateRefreshToken(User user) {

        return Jwts.builder()
                .subject(user.getLogin())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .signWith(getSignKey())
                .compact();
    }

    public String extractLogin(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token, User user) {

        String login = extractLogin(token);

        return login.equals(user.getLogin()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token)
                .getExpiration()
                .before(new Date());
    }

    private Claims extractAllClaims(String token) {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
