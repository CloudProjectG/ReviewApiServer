package com.example.cloudproject.reviewapiserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Reviews")
@IdClass(Review.ReviewPK.class)
@EqualsAndHashCode(exclude = {"storeId", "userId"})
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id
    @Column(name = "store_id")
    private Long storeId;

    @Id
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String menu;

    @Column(nullable = false)
    private Byte grade;

    @Column(nullable = false)
    private String comment;

    @Column(name = "hasgtag1_id", nullable = false)
    private Short hashtag1Id;

    @Column(name = "hasgtag2_id", nullable = false)
    private Short hashtag2Id;

    @Column(name = "hasgtag3_id", nullable = false)
    private Short hashtag3Id;

    @Column(name = "image_uuid", unique = true)
    private UUID imageUuid;

    @Column(name = "hidden", nullable = false)
    private Boolean isHidden;

    @Column(name = "anonymous", nullable = false)
    private Boolean isAnonymous;

    @Column(name = "created_at", nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

    @Data
    @NoArgsConstructor
    public static class ReviewPK implements Serializable {
        private Long storeId;
        private Long userId;
    }

    public List<Short> getHashtagIdList() {
        return Arrays.asList(
                this.hashtag1Id,
                this.hashtag2Id,
                this.hashtag3Id
        );
    }

}
