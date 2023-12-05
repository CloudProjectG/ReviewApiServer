package com.example.cloudproject.reviewapiserver.util;

import com.example.cloudproject.reviewapiserver.dto.ReviewDTO;
import com.example.cloudproject.reviewapiserver.dto.UserDTO;
import com.example.cloudproject.reviewapiserver.exception.AuthException;
import com.example.cloudproject.reviewapiserver.exception.type.AuthExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class WebClientUtil {

    private final WebClient webClient;

    public Long getUserIdFromToken(@Value("${network.user-auth}") String hostname,
                                   String token) {
        return webClient.mutate()
                .baseUrl("http://" + hostname + "/login")
                .defaultHeaders(headers -> headers.setBearerAuth(token))
                .build()
                .post()
                .exchangeToMono(response -> {
                    if (response.statusCode() == HttpStatus.OK) {
                        return response.bodyToMono(UserDTO.AuthorizedResponse.class);
                    } else {
                        throw new AuthException(AuthExceptionType.UNAUTHORIZED_TOKEN);
                    }
                })
                .block()
                .getId();
    }

    public void patchHashtagAndAverageGradeToStore(@Value("${network.store}") String hostname,
                                                   ReviewDTO.UpdateHashtagAndGradeRequest requestDTO) {
        webClient.mutate()
                .baseUrl("http://" + hostname + "/store/" + requestDTO.getStoreId() + "/hashtag-and-grade")
                .build()
                .patch()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO.getInfo())
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }
}
