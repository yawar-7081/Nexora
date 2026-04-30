package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, String> {
    boolean existsByProblemId(String problemId);
    void deleteByProblemId(String problemId);

    List<Submission> findByProblemId(String problemId);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.problem.id = :problemId")
    Integer countSubmissionsByProblemId(@Param("problemId") String problemId);

    @Query("SELECT COUNT(s) FROM Submission s WHERE s.problem.id = :problemId AND s.status = 'ACCEPTED'")
    Integer countAcceptedSubmissionsByProblemId(@Param("problemId") String problemId);
}
