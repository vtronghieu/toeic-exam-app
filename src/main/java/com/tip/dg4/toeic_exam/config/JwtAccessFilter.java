package com.tip.dg4.toeic_exam.config;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.models.enums.JwtType;
import com.tip.dg4.toeic_exam.services.JwtService;
import com.tip.dg4.toeic_exam.utils.ConfigUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAccessFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ExceptionConfig exceptionConfig;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!ConfigUtil.existsAPI(request.getRequestURI())) {
            exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E006, request.getRequestURI());
            return;
        }
        if (ConfigUtil.isRequestUriUseRefreshToken(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }
        jwtService.setJwtType(JwtType.ACCESS_TOKEN);
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            if (!ConfigUtil.publicRequestURI(request.getRequestURI())) {
                exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E002);
            } else {
                filterChain.doFilter(request, response);
            }
            return;
        }
        String token = authHeader.substring(BEARER_PREFIX.length());
        String username = jwtService.extractUsername(token, response);
        if (Objects.isNull(username)) return;
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.isNull(securityContext.getAuthentication())) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (!jwtService.isTokenValid(token, userDetails)) {
                exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E006);
                return;
            }
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            securityContext.setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
