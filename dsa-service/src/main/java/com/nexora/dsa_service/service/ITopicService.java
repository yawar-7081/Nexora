package com.nexora.dsa_service.service;

import com.nexora.dsa_service.dto.request.TopicRequestDto;
import com.nexora.dsa_service.dto.response.*;

import java.util.List;

public interface ITopicService {

    // Create
    TopicResponseDto createTopic(TopicRequestDto topicRequestDto);

    // Read
    TopicResponseDto getTopicById(String topicId);

    TopicResponseDto getTopicByName(String topicName);

    List<TopicResponseDto> getAllTopics();

    List<TopicResponseDto> searchTopics(String searchTerm);

    List<TopicWithStatsResponseDto> getAllTopicsWithStats();

    TopicWithStatsResponseDto getTopicWithStats(String topicId);

    // User Progress
    UserTopicProgressDto getUserTopicProgress(String topicId, String userId);

    // Update
    TopicResponseDto updateTopic(String topicId, TopicRequestDto topicRequestDto);

    // Delete
    Void deleteTopic(String topicId);
}
