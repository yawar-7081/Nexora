package com.nexora.dsa_service.service.impl;

import com.nexora.dsa_service.dto.request.TopicRequestDto;
import com.nexora.dsa_service.dto.response.*;
import com.nexora.dsa_service.entity.Problem;
import com.nexora.dsa_service.entity.Topic;
import com.nexora.dsa_service.entity.UserTopicStat;
import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import com.nexora.dsa_service.repository.SubmissionRepository;
import com.nexora.dsa_service.repository.TopicRepository;
import com.nexora.dsa_service.repository.UserTopicStatRepository;
import com.nexora.dsa_service.service.ITopicService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class TopicService implements ITopicService {

    private final TopicRepository topicRepository;
    private final UserTopicStatRepository userTopicStatRepository;
    private final SubmissionRepository submissionRepository;

    @Override
    @Transactional
    public TopicResponseDto createTopic(TopicRequestDto topicRequestDto) {
        try {
            log.info("Creating topic with name: {}", topicRequestDto.getName());

            // Check if topic already exists
            if (topicRepository.findByName(topicRequestDto.getName()).isPresent()) {
                throw new RuntimeException("Topic with name '" + topicRequestDto.getName() + "' already exists");
            }

            Integer count = topicRepository.getAllTopicCount()+1;

            log.info("Count of topics - {}",count);

            Topic topic = new Topic();
            topic.setId("TOPIC_0"+count);
            topic.setName(topicRequestDto.getName());

            Topic savedTopic = topicRepository.save(topic);
            log.info("Topic created successfully with ID: {}", savedTopic.getId());

            return buildTopicResponseDto(savedTopic);
        } catch (RuntimeException e) {
            log.error("Error creating topic: {}", e.getMessage());
            throw new RuntimeException("Failed to create topic: " + e.getMessage());
        }
    }

    @Override
    public TopicResponseDto getTopicById(String topicId) {
        try {
            log.info("Fetching topic with ID: {}", topicId);
            Topic topic = topicRepository.findById(topicId)
                    .orElseThrow(() -> new RuntimeException("Topic not found with ID: " + topicId));

            return buildTopicResponseDto(topic);
        } catch (RuntimeException e) {
            log.error("Error fetching topic: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch topic: " + e.getMessage());
        }
    }

    @Override
    public TopicResponseDto getTopicByName(String topicName) {
        try {
            log.info("Fetching topic with name: {}", topicName);
            Topic topic = topicRepository.findByName(topicName)
                    .orElseThrow(() -> new RuntimeException("Topic not found with name: " + topicName));

            return buildTopicResponseDto(topic);
        } catch (RuntimeException e) {
            log.error("Error fetching topic: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch topic: " + e.getMessage());
        }
    }

    @Override
    public List<TopicResponseDto> getAllTopics() {
        try {
            log.info("Fetching all topics ");


            Sort sort = Sort.by(Sort.Direction.ASC, "name");
            List<Topic> topics = topicRepository.findAll(sort);

            return topics.stream()
                    .map(this::buildTopicResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            log.error("Error fetching topics: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch topics: " + e.getMessage());
        }
    }

    @Override
    public List<TopicResponseDto> searchTopics(String searchTerm) {
        try {
            log.info("Searching topics with term: {}", searchTerm);

            List<Topic> topics = topicRepository
                    .findByNameContainingIgnoreCase(searchTerm.trim().toLowerCase());

            if(topics==null || topics.isEmpty()) throw new RuntimeException("No topics found matching search term: " + searchTerm);

            return topics.stream().map(t -> TopicResponseDto.builder().id(t.getId()).name(t.getName()).build()).toList();

        } catch (RuntimeException e) {
            log.error("Error searching topics: {}", e.getMessage());
            throw new RuntimeException("Failed to search topics: " + e.getMessage());
        }
    }

    @Override
    public List<TopicWithStatsResponseDto> getAllTopicsWithStats() {
        try {
            log.info("Fetching all topics with statistics");

            return topicRepository.findAll()
                    .stream()
                    .map(this::buildTopicWithStatsResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            log.error("Error fetching topics with stats: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch topics: " + e.getMessage());
        }
    }

    @Override
    public TopicWithStatsResponseDto getTopicWithStats(String topicId) {
        try {
            log.info("Fetching topic with stats - ID: {}", topicId);

            Topic topic = topicRepository.findById(topicId)
                    .orElseThrow(() -> new RuntimeException("Topic not found with ID: " + topicId));

            return buildTopicWithStatsResponseDto(topic);
        } catch (RuntimeException e) {
            log.error("Error fetching topic stats: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch topic stats: " + e.getMessage());
        }
    }


    @Override
    public UserTopicProgressDto getUserTopicProgress(String topicId, String userId) {
        try {
            log.info("Fetching user topic progress - topicId: {}, userId: {}", topicId, userId);

            Topic topic = topicRepository.findById(topicId)
                    .orElseThrow(() -> new RuntimeException("Topic not found with ID: " + topicId));

            UserTopicStat userTopicStat = userTopicStatRepository.findByUserIdAndTopicId(userId, topicId)
                    .orElse(new UserTopicStat());

            // Cannot get total problems without relationship in Topic entity
            long totalProblems = 0; // Cannot determine without relationship
            long solvedProblems = userTopicStat.getTotalSolvedCount();
            double completionPercentage = totalProblems == 0 ? 0 : (solvedProblems * 100.0) / totalProblems;
            double successRate = userTopicStat.getSuccessRate();

            return UserTopicProgressDto.builder()
                    .topicId(topicId)
                    .topicName(topic.getName())
                    .totalProblems((int) totalProblems)
                    .solvedProblems(userTopicStat.getTotalSolvedCount().intValue())
                    .easyAttempted(0)
                    .mediumAttempted(0)
                    .hardAttempted(0)
                    .easySolved(userTopicStat.getEasySolvedCount().intValue())
                    .mediumSolved(userTopicStat.getMediumSolvedCount().intValue())
                    .hardSolved(userTopicStat.getHardSolvedCount().intValue())
                    .completionPercentage(completionPercentage)
                    .successRate(successRate)
                    .build();
        } catch (RuntimeException e) {
            log.error("Error fetching user topic progress: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch user progress: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public TopicResponseDto updateTopic(String topicId, TopicRequestDto topicRequestDto) {
        try {
            log.info("Updating topic with ID: {}", topicId);

            Topic topic = topicRepository.findById(topicId)
                    .orElseThrow(() -> new RuntimeException("Topic not found with ID: " + topicId));

            // Check if new name already exists
            if (!topic.getName().equals(topicRequestDto.getName()) &&
                    topicRepository.findByName(topicRequestDto.getName()).isPresent()) {
                throw new RuntimeException("Topic with name '" + topicRequestDto.getName() + "' already exists");
            }

            topic.setName(topicRequestDto.getName());

            Topic updatedTopic = topicRepository.save(topic);
            log.info("Topic updated successfully");

            return buildTopicResponseDto(updatedTopic);
        } catch (RuntimeException e) {
            log.error("Error updating topic: {}", e.getMessage());
            throw new RuntimeException("Failed to update topic: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Void deleteTopic(String topicId) {
        try {
            log.info("Deleting topic with ID: {}", topicId);

            Topic topic = topicRepository.findById(topicId)
                    .orElseThrow(() -> new RuntimeException("Topic not found with ID: " + topicId));

            // Cannot remove topic from problems without relationship in Topic entity
            // This operation is not supported without proper relationships

            topicRepository.delete(topic);
            log.info("Topic deleted successfully");

            return null;
        } catch (RuntimeException e) {
            log.error("Error deleting topic: {}", e.getMessage());
            throw new RuntimeException("Failed to delete topic: " + e.getMessage());
        }
    }

    // Helper methods
    private TopicResponseDto buildTopicResponseDto(Topic topic) {
        return TopicResponseDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .build();
    }

    private TopicWithStatsResponseDto buildTopicWithStatsResponseDto(Topic topic) {
        // Cannot get problem statistics without relationship
        return TopicWithStatsResponseDto.builder()
                .id(topic.getId())
                .name(topic.getName())
                .totalProblems(0)
                .easyProblems(0)
                .mediumProblems(0)
                .hardProblems(0)
                .acceptanceRate(0)
                .createdAt(topic.getCreatedAt())
                .build();
    }
}
