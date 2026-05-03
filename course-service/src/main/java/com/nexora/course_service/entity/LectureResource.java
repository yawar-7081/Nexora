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
        name = "lecture_resource_tx",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"lecture_id","title"}),
                @UniqueConstraint(columnNames = {"lecture_id","resourceUrl"})
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureResource 
        {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String resourceUrl;
    private String publicId;
    private Long size;
    private String format;
    @Enumerated(EnumType.STRING)
    private ResourceType type;

    @Column(nullable = false)
    private String title;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "lecture_id",nullable = false)
    private Lecture lecture;
}
}
