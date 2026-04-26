package com.nexora.dsa_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "test_case_tx",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"problem_id", "order_index"})}
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestCase extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String inputData;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String outputData;

    @Column(nullable = false)
    private Boolean isHidden = false;

    @Column(nullable = false)
    private Integer orderIndex;

}
