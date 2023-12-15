package com.example.cloudproject.reviewapiserver.dto;

import lombok.*;

import java.util.List;

public class StoreNameDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Info {

        private Long id;
        private String name;

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        List<Long> storeIdList;

        public String getQuery() {
            List<String> stringList = storeIdList.stream()
                    .map(String::valueOf)
                    .toList();

            return "storeIdList=" + String.join(",", stringList);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {

        List<StoreNameDTO.Info> result;

    }

}
