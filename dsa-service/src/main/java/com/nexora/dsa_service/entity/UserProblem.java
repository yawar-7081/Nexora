package com.nexora.dsa_service.entity;

import com.nexora.dsa_service.entity.enums.ProblemSolveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_problem_tx",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "problem_id"})}
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserProblem extends BaseEntity{

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemSolveStatus status = ProblemSolveStatus.NOT_STARTED;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long submissionCount = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long acceptedSubmissionCount = 0L;

    @Column(name = "first_attempted_at", nullable = true)
    private LocalDateTime firstAttemptedAt;

    @Column(name = "last_attempted_at", nullable = true)
    private LocalDateTime lastAttemptedAt;

    @Column(name = "solved_at", nullable = true)
    private LocalDateTime solvedAt;

}
