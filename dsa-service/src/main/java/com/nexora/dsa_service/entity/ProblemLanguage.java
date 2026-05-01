package com.nexora.dsa_service.entity;

import com.nexora.dsa_service.entity.enums.CodingLanguage;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        name = "problem_language_tx",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"problem_id", "language"})}
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProblemLanguage extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CodingLanguage language;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String functionSignature;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String templateCode;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String driverCode;
}
