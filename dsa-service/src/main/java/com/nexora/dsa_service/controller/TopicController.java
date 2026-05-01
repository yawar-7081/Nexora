package com.nexora.dsa_service.controller;

import com.nexora.dsa_service.dto.request.TopicRequestDto;
import com.nexora.dsa_service.dto.response.*;
import com.nexora.dsa_service.service.ITopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/topics")
@RequiredArgsConstructor
public class TopicController {

    private final ITopicService topicService;

    // Create
    @PostMapping
    public ResponseEntity<TopicResponseDto> createTopic( @RequestBody TopicRequestDto topicRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(topicService.createTopic(topicRequestDto));
    }

    // Get All Topics (Paginated)
    @GetMapping
    public ResponseEntity<List<TopicResponseDto>> getAllTopics() {
        return ResponseEntity.ok(topicService.getAllTopics());
    }

    // Search Topics
    @GetMapping("/search")
    public ResponseEntity<List<TopicResponseDto>> searchTopics(
            @RequestParam String searchTerm) {
        return ResponseEntity.ok(topicService.searchTopics(searchTerm));
    }

    // Get Topic by ID
    @GetMapping("/{topicId}")
    public ResponseEntity<TopicResponseDto> getTopicById(@PathVariable String topicId) {
        return ResponseEntity.ok(topicService.getTopicById(topicId));
    }

    // Get Topic by Name
    @GetMapping("/name/{topicName}")
    public ResponseEntity<TopicResponseDto> getTopicByName(@PathVariable String topicName) {
        return ResponseEntity.ok(topicService.getTopicByName(topicName));
    }

    // Get All Topics with Statistics
    @GetMapping("/stats/all")
    public ResponseEntity<List<TopicWithStatsResponseDto>> getAllTopicsWithStats() {
        return ResponseEntity.ok(topicService.getAllTopicsWithStats());
    }

    // Get Topic with Statistics
    @GetMapping("/{topicId}/stats")
    public ResponseEntity<TopicWithStatsResponseDto> getTopicWithStats(@PathVariable String topicId) {
        return ResponseEntity.ok(topicService.getTopicWithStats(topicId));
    }


    // Get User's Progress in Topic
    @GetMapping("/{topicId}/user-progress/{userId}")
    public ResponseEntity<UserTopicProgressDto> getUserTopicProgress(
            @PathVariable String topicId,
            @PathVariable String userId) {
        return ResponseEntity.ok(topicService.getUserTopicProgress(topicId, userId));
    }

    // Update Topic
    @PutMapping("/{topicId}")
    public ResponseEntity<TopicResponseDto> updateTopic(
            @PathVariable String topicId,
             @RequestBody TopicRequestDto topicRequestDto) {
        return ResponseEntity.ok(topicService.updateTopic(topicId, topicRequestDto));
    }

    // Delete Topic
    @DeleteMapping("/{topicId}")
    public ResponseEntity<Void> deleteTopic(@PathVariable String topicId) {
        return ResponseEntity.ok(topicService.deleteTopic(topicId));
    }
}
