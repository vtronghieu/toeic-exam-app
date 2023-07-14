package com.tip.dg4.toeic_exam.config;

import com.tip.dg4.toeic_exam.common.constants.TExamApiConstant;
import com.tip.dg4.toeic_exam.common.constants.TExamExceptionConstant;
import com.tip.dg4.toeic_exam.exceptions.UnauthorizedException;
import com.tip.dg4.toeic_exam.services.JwtService;
import com.tip.dg4.toeic_exam.utils.ApiUtil;
import com.tip.dg4.toeic_exam.utils.TExamUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
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
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final ExceptionConfig exceptionConfig;
    private final RequestMappingHandlerMapping handlerMapping;

    public JwtAuthFilter(JwtService jwtService,
                         UserDetailsService userDetailsService,
                         ExceptionConfig exceptionConfig,
                         RequestMappingHandlerMapping handlerMapping) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.exceptionConfig = exceptionConfig;
        this.handlerMapping = handlerMapping;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        Cookie authCookie = TExamUtil.getAuthCookie(request);
        if (Objects.isNull(authCookie)) {
            String requestUri = request.getRequestURI();
            if (ApiUtil.existAPI(handlerMapping, requestUri) && !isRequestUriAllowed(requestUri)) {
                exceptionConfig.handleUnauthorizedException(response, new UnauthorizedException(TExamExceptionConstant.TEXAM_E002));
                return;
            }
            filterChain.doFilter(request, response);
            return;
        }
        String token = authCookie.getValue();
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
                TExamApiConstant.ACCOUNT_API_ROOT_LOGIN,
                TExamApiConstant.ACCOUNT_API_ROOT_REGISTER
        );

        return requestUris.contains(requestUri);
    }
}
