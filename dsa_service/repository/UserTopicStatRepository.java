package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.UserTopicStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserTopicStatRepository extends JpaRepository<UserTopicStat, String> {
    UserTopicStat findByUserId(String userId);

    @Query("SELECT uts FROM UserTopicStat uts " +
           "WHERE uts.userId = :userId AND uts.topic.id = :topicId")
    Optional<UserTopicStat> findByUserIdAndTopicId(
        @Param("userId") String userId,
        @Param("topicId") Long topicId
    );
}
