package com.nexora.dsa_service.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
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
public class Topic{

    @Column(columnDefinition = "TEXT", nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToMany(mappedBy = "topics")
    private Set<Problem> problems = new HashSet<>();

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

}
