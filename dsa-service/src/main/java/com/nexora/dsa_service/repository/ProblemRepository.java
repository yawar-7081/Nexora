package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.Problem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem,String>, JpaSpecificationExecutor<Problem> {
}
