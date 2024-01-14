package com.tip.dg4.toeic_exam.services;

import com.tip.dg4.toeic_exam.models.enums.JwtType;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

public interface JwtService {
    String generateToken(String username, JwtType jwtType);

    String generateToken(UserDetails userDetails, JwtType jwtType);

    String extractUsername(String token);

    String extractUsername(String token, HttpServletResponse response);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    boolean isTokenValid(String token, UserDetails userDetails);
}
