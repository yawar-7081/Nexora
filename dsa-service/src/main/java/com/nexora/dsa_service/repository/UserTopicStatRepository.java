package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.UserTopicStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTopicStatRepository extends JpaRepository<UserTopicStat,String> {
    UserTopicStat findByUserId(String userId);

    UserTopicStat findByUserIdAndTopicId(String userId, String id);
}
