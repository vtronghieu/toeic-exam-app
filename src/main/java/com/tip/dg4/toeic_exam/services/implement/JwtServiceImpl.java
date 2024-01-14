package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.config.ExceptionConfig;
import com.tip.dg4.toeic_exam.models.enums.JwtType;
import com.tip.dg4.toeic_exam.services.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final long ACCESS_TOKEN_EXPIRATION = 1000 * 60 * 60L; // 1 hour
    private static final long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 3L; // 3 days

    private final ExceptionConfig exceptionConfig;

    @Override
    public String generateToken(String username, JwtType jwtType) {
        return createToken(new HashMap<>(), username, jwtType);
    }

    @Override
    public String generateToken(UserDetails userDetails, JwtType jwtType) {
        return createToken(new HashMap<>(), userDetails.getUsername(), jwtType);
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractUsername(String token, HttpServletResponse response) {
        try {
            return extractClaim(token, Claims::getSubject);
        } catch (SignatureException e) {
            exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E010);
        } catch (MalformedJwtException e) {
            exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E006);
        } catch (ExpiredJwtException e) {
            exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E007);
        } catch (UnsupportedJwtException e) {
            exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E008);
        } catch (IllegalArgumentException e) {
            exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E009);
        }
        return null;
    }

    @Override
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private String createToken(Map<String, Object> claims, String username, JwtType jwtType) {
        Date expirationDate = new Date(System.currentTimeMillis() +
                (JwtType.REFRESH_TOKEN.equals(jwtType) ? REFRESH_TOKEN_EXPIRATION : ACCESS_TOKEN_EXPIRATION));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(expirationDate)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(System.getenv("JWT_ACCESS_TOKEN_SECRET")));
    }
}
