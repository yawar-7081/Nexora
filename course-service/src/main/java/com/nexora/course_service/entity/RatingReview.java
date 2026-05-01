package com.nexora.course_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "rating_review_tx",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"course_id","user_id"})}
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingReview {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false,updatable = false)
    private String userId;

    @Column(nullable = false)
    private String review;

    @Column(nullable = false)
    private Integer rating;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;
}
