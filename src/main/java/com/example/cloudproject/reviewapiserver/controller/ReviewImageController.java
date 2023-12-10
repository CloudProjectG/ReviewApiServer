package com.example.cloudproject.reviewapiserver.controller;

import com.example.cloudproject.reviewapiserver.dto.ImageDTO;
import com.example.cloudproject.reviewapiserver.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/review/image")
public class ReviewImageController {

    private final ImageService imageService;

    @GetMapping("/{imageUuid}")
    public ResponseEntity<byte[]> getImage(@PathVariable("imageUuid") UUID imageUuid) {
        ImageDTO.DownloadRequest requestDTO = ImageDTO.DownloadRequest.builder()
                .imageUuid(imageUuid)
                .build();

        System.out.println(imageUuid.toString());

        ImageDTO.DownloadResponse responseDTO = imageService.downloadImage(requestDTO);

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, responseDTO.getContentType())
                .body(responseDTO.getContent());
    }

    @PostMapping("/{imageUuid}")
    public ResponseEntity<ImageDTO.UploadResponse> postImage(@PathVariable("imageUuid") UUID imageUuid,
                                                             @RequestPart("image")MultipartFile image) {

        ImageDTO.UploadRequest requestDTO = ImageDTO.UploadRequest.builder()
                .imageUuid(imageUuid)
                .image(image)
                .build();

        return ResponseEntity.ok(imageService.uploadImage(requestDTO));
    }
}
