package com.example.cloudproject.reviewapiserver.util;

import com.example.cloudproject.reviewapiserver.dto.ReviewDTO;
import com.example.cloudproject.reviewapiserver.dto.StoreNameDTO;
import com.example.cloudproject.reviewapiserver.dto.UserDTO;
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

    @Value("${network.user-auth}")
    private String userAuthHostname;

    @Value("${network.store}")
    private String storeHostname;


    public UserDTO.AuthorizedResponse getUserIdFromToken(String token) {
        UserDTO.AuthorizedRequest requestDTO = UserDTO.AuthorizedRequest.builder()
                .token(token)
                .build();

        return webClient.mutate()
                .baseUrl("http://" + userAuthHostname + "/authorization")
                .build()
                .post()
                .bodyValue(requestDTO)
                .exchangeToMono(response -> {
                    if (response.statusCode() == HttpStatus.OK) {
                        return response.bodyToMono(UserDTO.AuthorizedResponse.class);
                    } else {
                        return null;
                    }
                })
                .block();
    }

    public void patchHashtagAndAverageGradeToStore(ReviewDTO.UpdateHashtagAndGradeRequest requestDTO) {
        webClient.mutate()
                .baseUrl("http://" + storeHostname + "/store/" + requestDTO.getStoreId() + "/hashtag-and-grade")
                .build()
                .patch()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(requestDTO.getInfo())
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

    public StoreNameDTO.Response getStoreNameList(StoreNameDTO.Request requestDTO) {
        return webClient.mutate()
                .baseUrl("http://" + storeHostname + "/store/name?" + requestDTO.getQuery())
                .build()
                .get()
                .retrieve()
                .bodyToMono(StoreNameDTO.Response.class)
                .block();
    }
}
