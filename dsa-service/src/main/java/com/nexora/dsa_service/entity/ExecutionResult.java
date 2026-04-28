package com.nexora.dsa_service.entity;

import com.nexora.dsa_service.entity.enums.ExecutionResultStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "execution_result_tx")
@Getter
@Setter
@NoArgsConstructor
public class ExecutionResult extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_case_id", nullable = false)
    private TestCase testCase;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String input;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String expectedOutput;

    @Column(columnDefinition = "TEXT")
    private String actualOutput;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExecutionResultStatus status;

    @Column
    private Float executionTime;   // optional (per test case)

    @Column
    private Integer memoryUsed;   // optional
}