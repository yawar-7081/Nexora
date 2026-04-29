package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {
    boolean existsByProblemId(String problemId);
    void deleteByProblemId(String problemId);

    List<Submission> findByProblemId(String problemId);
}
