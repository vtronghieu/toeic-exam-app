package com.tip.dg4.toeic_exam.services.implement;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.config.ExceptionConfig;
import com.tip.dg4.toeic_exam.services.JwtService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
    private static final String SECRET_KEY = "4D6251655468576D5A7133743677397A24432646294A404E635266556A586E32";
    private static final long EXPIRATION_TIMES = 1000 * 60 * 60 * 24 * 7L; // 7 days

    private final ExceptionConfig exceptionConfig;

    @Override
    public String generateToken(String username) {
        return createToken(new HashMap<>(), username);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return createToken(new HashMap<>(), userDetails.getUsername());
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public String extractUsername(String token, HttpServletResponse response) {
        try {
            return extractClaim(token, Claims::getSubject);
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

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIMES))
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
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
    }
}
