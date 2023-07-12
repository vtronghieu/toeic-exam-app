package com.tip.dg4.toeic_exam.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;


@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiUtil {
    public static boolean existAPI(RequestMappingHandlerMapping handlerMapping, String requestUri) {
        Set<RequestMappingInfo> requestMappingInfo = handlerMapping.getHandlerMethods().keySet();

        for (RequestMappingInfo mappingInfo : requestMappingInfo) {
            Set<String> patterns = Objects.nonNull(mappingInfo.getPathPatternsCondition()) ?
                                   mappingInfo.getPathPatternsCondition().getPatternValues() :
                                   Collections.emptySet();
            boolean isApiMatch = !patterns.isEmpty() && patterns.stream().anyMatch(requestUri::equals);

            if (isApiMatch) return true;
        }

        return false;
    }
}
