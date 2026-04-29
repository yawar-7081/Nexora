package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.UserProblem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProblemRepository extends JpaRepository<UserProblem,String> {
    void deleteByProblemId(String problemId);

    UserProblem findByProblemId(String problemId);
}
