package com.nexora.dsa_service.repository;

import com.nexora.dsa_service.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic,String> {
        @Query("SELECT t FROM Topic t where t.name LIKE '%:name%'")
        Optional<Topic> findByName(@Param(value = "name") String name);

    List<Topic> findByNameIn(List<String> names);
}
