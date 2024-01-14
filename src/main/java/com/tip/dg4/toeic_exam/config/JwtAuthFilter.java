package com.tip.dg4.toeic_exam.config;

import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
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
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ExceptionConfig exceptionConfig;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        if (!ConfigUtil.existsAPI(request.getRequestURI())) {
            exceptionConfig.handleJwtException(response, ExceptionConstant.TEXAM_E002, request.getRequestURI());
            return;
        }
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            if (!isAllowedRequestURI(request.getRequestURI())) {
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
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                securityContext.setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isAllowedRequestURI(String requestUri) {
        Set<String> requestUris = ConfigUtil.getMethodsAndPublicAPIs().values()
                .parallelStream().flatMap(Arrays::stream)
                .collect(Collectors.toSet());
        Set<String> swaggerUris = Set.of(
                "/v2/api-docs",
                "/v3/api-docs",
                "/swagger-resources",
                "/swagger-config",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui",
                "/webjars"
        );

        return requestUris.contains(requestUri) || swaggerUris.stream().anyMatch(requestUri::startsWith);
    }
}
