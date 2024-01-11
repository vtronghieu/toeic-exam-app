package com.tip.dg4.toeic_exam.config;

import com.tip.dg4.toeic_exam.common.constants.ApiConstant;
import com.tip.dg4.toeic_exam.common.constants.ExceptionConstant;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.services.JwtService;
import com.tip.dg4.toeic_exam.utils.ApiUtil;
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
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    public static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ExceptionConfig exceptionConfig;
    private final RequestMappingHandlerMapping handlerMapping;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (Objects.isNull(authHeader) || !authHeader.startsWith(BEARER_PREFIX)) {
            String requestUri = request.getRequestURI();
            if (ApiUtil.existAPI(handlerMapping, requestUri) && !isRequestUriAllowed(requestUri)) {
                exceptionConfig.handleUnauthorizedException(response, new UnauthorizedException(ExceptionConstant.TEXAM_E002));
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        String token = authHeader.substring(BEARER_PREFIX.length());
        String username = jwtService.extractUsername(token);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (Objects.nonNull(username) && Objects.isNull(securityContext.getAuthentication())) {
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

    private boolean isRequestUriAllowed(String requestUri) {
        Set<String> requestUris = Set.of(
                ApiConstant.AUTH_API_LOGIN,
                ApiConstant.AUTH_API_REGISTER
        );
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
