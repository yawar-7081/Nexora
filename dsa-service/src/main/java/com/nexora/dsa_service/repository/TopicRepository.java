package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, String> {

    @Query("SELECT t FROM Topic t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Optional<Topic> findByName(@Param("name") String name);

    List<Topic> findByNameIn(List<String> names);

    @Query("SELECT t FROM Topic t WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Topic> searchTopics(@Param("searchTerm") String searchTerm, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Topic t JOIN t.problems p WHERE t.id = :topicId")
    Integer getProblemCountByTopicId(@Param("topicId") String topicId);

    @Query("SELECT distinct COUNT(t) FROM Topic t")
    Integer getAllTopicCount();

    List<Topic> findByNameContainingIgnoreCase(String searchTerm);
}
