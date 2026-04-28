package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.ExecutionResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExecutionResultRepository extends JpaRepository<ExecutionResult,String> {
}
