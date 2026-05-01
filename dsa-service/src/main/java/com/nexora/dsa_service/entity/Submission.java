package com.nexora.dsa_service.entity;

import com.nexora.dsa_service.entity.enums.CodingLanguage;
import com.nexora.dsa_service.entity.enums.SubmissionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "submission_tx"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Submission extends BaseEntity{

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CodingLanguage language;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SubmissionStatus status = SubmissionStatus.PENDING;

    @Column(nullable = true)
    private Long executionTime;

    @Column(nullable = true)
    private Long memoryUsed;
}
