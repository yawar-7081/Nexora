package com.nexora.dsa_service.entity;

import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import com.nexora.dsa_service.entity.enums.ProblemStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "problems_tx"
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Problem extends BaseEntity{

    @Column(columnDefinition = "TEXT", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemDificulty problemDificulty;

    @ElementCollection
    @CollectionTable(name = "problem_constraints_tx", joinColumns = @JoinColumn(name = "problem_id"))
    @Column(name = "constraints", columnDefinition = "TEXT")
    private Set<String> constraints = new HashSet<>();

    @Column(nullable = false)
    private Integer timeLimit;

    @Column(nullable = false)
    private Integer memoryLimit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProblemStatus status;

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ProblemLanguage> problemLanguages = new HashSet<>();

    @OneToMany(mappedBy = "problem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<TestCase> testCases = new HashSet<>();



    @ManyToMany
    @JoinTable(
            name = "problem_topic_tx",
            joinColumns = @JoinColumn(name = "problem_id"),
            inverseJoinColumns = @JoinColumn(name = "topic_id")
    )
    private Set<Topic> topics  = new HashSet<>();
}
