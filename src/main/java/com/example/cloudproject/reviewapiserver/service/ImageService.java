package com.example.cloudproject.reviewapiserver.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.util.IOUtils;
import com.example.cloudproject.reviewapiserver.dto.ImageDTO;
import com.example.cloudproject.reviewapiserver.exception.ImageException;
import com.example.cloudproject.reviewapiserver.exception.type.ImageExceptionType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public ImageDTO.UploadResponse uploadImage(ImageDTO.UploadRequest requestDTO) {
        if (existImage(requestDTO.getImageUuid())) {
            throw new ImageException(ImageExceptionType.IMAGE_EXIST);
        }

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(requestDTO.getImage().getContentType());
        objectMetadata.setContentLength(requestDTO.getImage().getSize());

        try {
            amazonS3Client.putObject(bucket, requestDTO.getKey(), requestDTO.getImage().getInputStream(), objectMetadata);
        } catch (IOException ioe) {
            throw new ImageException(ImageExceptionType.UPLOAD_FAILURE);
        }

        return ImageDTO.UploadResponse.builder().build();
    }

    public ImageDTO.DownloadResponse downloadImage(ImageDTO.DownloadRequest requestDTO) {
        if (!existImage(requestDTO.getImageUuid())) {
            throw new ImageException(ImageExceptionType.IMAGE_NOT_FOUND);
        }

        S3Object object = amazonS3Client.getObject(bucket, requestDTO.getKey());
        ImageDTO.DownloadResponse responseDTO;
        try {
            responseDTO = ImageDTO.DownloadResponse.builder()
                    .contentType(object.getObjectMetadata().getContentType())
                    .content(IOUtils.toByteArray(object.getObjectContent()))
                    .build();
        } catch (IOException ioe) {
            throw new ImageException(ImageExceptionType.DOWNLOAD_FAILURE);
        }

        return responseDTO;
    }

    public boolean existImage(UUID imageUuid) {
        return amazonS3Client.doesObjectExist(bucket, imageUuid.toString());
    }

    public void deleteImage(UUID imageUuid) {
        amazonS3Client.deleteObject(bucket, imageUuid.toString());
    }
}
