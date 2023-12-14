package com.example.cloudproject.reviewapiserver.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;

@Data
@EqualsAndHashCode
@AllArgsConstructor
@Builder
public class PageDTO {

    private Integer page;
    private Integer row;
    private Integer elements;
    private Long totalElements;
    private Integer totalPages;

    public static <T> PageDTO from(Page<T> page) {
        return PageDTO.builder()
                .page(page.getNumber())
                .row(page.getSize())
                .elements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .build();
    }

}
