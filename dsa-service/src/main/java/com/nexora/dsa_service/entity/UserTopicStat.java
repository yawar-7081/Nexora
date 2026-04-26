package com.nexora.dsa_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_topic_stat_tx",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "topic_id"})}
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserTopicStat extends BaseEntity{


    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long easySolvedCount = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long mediumSolvedCount = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long hardSolvedCount = 0L;

    @Column(nullable = false, columnDefinition = "BIGINT DEFAULT 0")
    private Long totalAttempts = 0L;

    @Column(name = "last_attempted_at", nullable = true)
    private LocalDateTime lastAttemptedAt;

    public Long getTotalSolvedCount(){
        return (easySolvedCount != null ? easySolvedCount : 0) +
                (mediumSolvedCount != null ? mediumSolvedCount : 0) +
                (hardSolvedCount != null ? hardSolvedCount : 0);
    }

    public Double getSuccessRate(){
        if(totalAttempts == null || totalAttempts == 0) return 0.0;
        return (getTotalSolvedCount() * 100.0) / totalAttempts;
    }

}
