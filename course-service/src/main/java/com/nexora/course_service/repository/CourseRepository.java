package com.nexora.course_service.repository;

import com.nexora.course_service.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,String> {
    boolean existsByTitle(String title);
}
