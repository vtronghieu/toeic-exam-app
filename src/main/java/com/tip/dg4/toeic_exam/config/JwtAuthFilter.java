package com.tip.dg4.toeic_exam.config;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.exceptions.ForbiddenException;
import com.tip.dg4.toeic_exam.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Set;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN_PREFIX = "Bearer ";

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ExceptionConfig exceptionConfig;

    public JwtAuthFilter(JwtService jwtService,
                         UserDetailsService userDetailsService,
                         ExceptionConfig exceptionConfig) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.exceptionConfig = exceptionConfig;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(AUTHORIZATION_HEADER);
        String token;
        String username;
        if (authHeader == null || !authHeader.startsWith(BEARER_TOKEN_PREFIX)) {
            if (!isRequestUrlAllowed(request.getRequestURI())) {
                exceptionConfig.handleForbiddenException(response, new ForbiddenException(TExamExceptionConstant.TEXAM_E002));
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        token = authHeader.substring(BEARER_TOKEN_PREFIX.length());
        username = jwtService.extractUsername(token);
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities()
                );
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private boolean isRequestUrlAllowed(String requestUrl) {
        Set<String> requestUrls = Set.of(
                TExamApiConstant.ACCOUNT_API_ROOT_LOGIN,
                TExamApiConstant.ACCOUNT_API_ROOT_REGISTER
        );

        return requestUrls.contains(requestUrl);
    }
}
