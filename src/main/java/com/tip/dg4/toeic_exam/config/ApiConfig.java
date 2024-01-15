package com.tip.dg4.toeic_exam.config;

import com.tip.dg4.toeic_exam.annotations.PublicApi;
import com.tip.dg4.toeic_exam.annotations.UseRefreshToken;
import com.tip.dg4.toeic_exam.utils.ConfigUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import java.util.Map;

@Configuration
public class ApiConfig {
    @Bean
    public void setRequestUriUseRefreshToken() {
        Map<HttpMethod, String[]> requestURIs = ConfigUtil.getMethodsAndAPIsByAnnotation(UseRefreshToken.class);

        ConfigUtil.setRequestUriUsesRefreshToken(requestURIs);
    }

    @Bean
    public void setPublicRequestUri() {
        Map<HttpMethod, String[]> requestURIs = ConfigUtil.getMethodsAndAPIsByAnnotation(PublicApi.class);

        ConfigUtil.setPublicRequestURIs(requestURIs);
    }
}
