package com.nexora.dsa_service.repository.specification;

import com.nexora.dsa_service.entity.Problem;
import com.nexora.dsa_service.entity.Topic;
import com.nexora.dsa_service.entity.UserProblem;
import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Set;


public class ProblemSpecification {

    public static Specification<Problem> hasTitle(String title){
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("title")),"%"+title.toLowerCase()+"%");
    }

    public static Specification<Problem> hasDifficulty(Set<ProblemDificulty> problemDificulty){
        return (root,query,criteriaBuilder) ->
                criteriaBuilder.in(root.get("problemDificulty")).value(problemDificulty);
    }

    public static Specification<Problem> hasTopics(List<Topic> topics){
        return ((root, query, criteriaBuilder) -> {
            assert query != null;
            query.distinct(true);

            Join<Problem,Topic> topicJoin = root.join("topics");

           return criteriaBuilder.equal(topicJoin.get("name"),topics);
        });
    }
}
