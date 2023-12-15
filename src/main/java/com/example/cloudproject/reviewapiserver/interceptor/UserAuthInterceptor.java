package com.example.cloudproject.reviewapiserver.interceptor;

import com.example.cloudproject.reviewapiserver.dto.UserDTO;
import com.example.cloudproject.reviewapiserver.util.WebClientUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class UserAuthInterceptor implements HandlerInterceptor {

    private final WebClientUtil webClientUtil;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) {

        String requestURI = request.getRequestURI();

        if (!pathMatcher.match("/review/*", requestURI)
                && !pathMatcher.match("/review/image/*", requestURI)) {
            return false;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
        }

        UserDTO.AuthorizedResponse authResponse;

        if (token == null || (authResponse = webClientUtil.getUserIdFromToken(token)) == null || !authResponse.getIsKhu()) {
            request.setAttribute("userId", -1L);
        } else {
            request.setAttribute("userId", authResponse.getPK());
        }

        return true;
    }
}
