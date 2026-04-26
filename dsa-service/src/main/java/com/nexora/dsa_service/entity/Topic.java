package com.nexora.dsa_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
        name = "topic_tx",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name"})}
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Topic extends BaseEntity{

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "problem_topic_tx",
            joinColumns = @JoinColumn(name = "topic_id"),
            inverseJoinColumns = @JoinColumn(name = "problem_id")
    )
    private Set<Problem> problems = new HashSet<>();
}
