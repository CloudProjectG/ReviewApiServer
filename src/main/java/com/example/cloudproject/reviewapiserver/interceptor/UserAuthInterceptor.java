package com.example.cloudproject.reviewapiserver.interceptor;

import com.example.cloudproject.reviewapiserver.dto.UserDTO;
import com.example.cloudproject.reviewapiserver.util.WebClientUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class UserAuthInterceptor implements HandlerInterceptor {

    WebClientUtil webClientUtil;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        if (!pathMatcher.match("/review/*", requestURI)) {
            return false;
        }

        if (request.getMethod().equalsIgnoreCase("GET")
                && !pathMatcher.match("/review/mine", requestURI)) {
            return true;
        }

        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token != null && token.startsWith("Bearer")) {
            token = token.substring(7);
        }

        UserDTO.AuthorizedResponse authResponse;


        if (token == null || (authResponse = webClientUtil.getUserIdFromToken(token)) == null) {
            request.setAttribute("userId", -1L);
        } else {
            request.setAttribute("userId", authResponse.getId());
        }

        return true;
    }
}
