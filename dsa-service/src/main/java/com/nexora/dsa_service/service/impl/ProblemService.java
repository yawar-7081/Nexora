package com.nexora.dsa_service.service.impl;

import com.nexora.dsa_service.dto.request.ProblemRequestDto;
import com.nexora.dsa_service.dto.response.ProblemResponseDto;
import com.nexora.dsa_service.entity.Problem;
import com.nexora.dsa_service.entity.ProblemLanguage;
import com.nexora.dsa_service.entity.TestCase;
import com.nexora.dsa_service.entity.Topic;
import com.nexora.dsa_service.entity.enums.ProblemStatus;
import com.nexora.dsa_service.repository.ProblemRepository;
import com.nexora.dsa_service.repository.TopicRepository;
import com.nexora.dsa_service.service.IProblemService;
import com.nexora.dsa_service.transformer.ProblemTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemService implements IProblemService {

    private final ProblemRepository problemRepository;
    private final TopicRepository topicRepository;

    @Override
    @Transactional
    public ProblemResponseDto createProblem(ProblemRequestDto problemRequestDto) {

        try{
            Problem newProblem = new Problem();
            newProblem.setTitle(problemRequestDto.getTitle());
            newProblem.setDescription(problemRequestDto.getDescription());
            newProblem.setProblemDificulty(problemRequestDto.getDifficulty());
            newProblem.setConstraints(problemRequestDto.getConstraints());
            newProblem.setTimeLimit(problemRequestDto.getTimeLimit());
            newProblem.setMemoryLimit(problemRequestDto.getMemoryLimit());
            newProblem.setStatus(ProblemStatus.ACTIVE);

            Set<Topic> topics = findTopics(problemRequestDto.getTopicNames());
            newProblem.setTopics(topics);

            Set<ProblemLanguage> problemLanguages = createProblemLanguages(problemRequestDto.getProblemLanguages());
            newProblem.setProblemLanguages(problemLanguages);
            for (ProblemLanguage pl : problemLanguages) {
                pl.setProblem(newProblem);
            }

            Set<TestCase> testCases = buildTestCases(problemRequestDto.getTestCases());
            newProblem.setTestCases(testCases);
            for (TestCase tc : testCases) {
                tc.setProblem(newProblem);
            }


            Problem savedProblem = problemRepository.save(newProblem);

            return ProblemTransformer.entityToResponse(savedProblem);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ProblemResponseDto getProblemById(String problemId) {
        try{
            log.info("Fetching problem with ID: {}", problemId);
            Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new RuntimeException("Problem Not Found with ID: " + problemId));
            log.info("Problem found: {}", problem.getTitle());
            return ProblemTransformer.entityToResponse(problem);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<ProblemLanguage> createProblemLanguages(List<ProblemRequestDto.ProblemLanguageRequest> problemLanguageRequests){
        return problemLanguageRequests.stream().map(plr -> {
            ProblemLanguage problemLanguage = new ProblemLanguage();
            problemLanguage.setLanguage(plr.getCodingLanguage());
            problemLanguage.setDriverCode(plr.getDriverCode());
            problemLanguage.setFunctionSignature(plr.getFunctionSignature());
            problemLanguage.setTemplateCode(plr.getTemplateCode());
            return problemLanguage;
        }).collect(Collectors.toSet());
    }

    private Set<TestCase> buildTestCases(List<ProblemRequestDto.TestCaseRequest> testCaseRequestList){
        return testCaseRequestList.stream().map(tc -> {
            TestCase testCase = new TestCase();
            testCase.setInputData(tc.getInputData());
            testCase.setOutputData(tc.getOutputData());
            testCase.setIsHidden(tc.isHidden());
            testCase.setOrderIndex(tc.getOrderIndex());
            return testCase;
        }).collect(Collectors.toSet());
    }

    private Set<Topic> findTopics(List<String> topicNames) {

        if (topicNames == null || topicNames.isEmpty()) {
            throw new RuntimeException("At least one topic is required");
        }

        List<Topic> topicsFromDb = topicRepository.findByNameIn(topicNames);

        // 🔥 validation
        if (topicsFromDb.size() != topicNames.size()) {

            Set<String> foundNames = topicsFromDb.stream()
                    .map(Topic::getName)
                    .collect(Collectors.toSet());

            List<String> missing = topicNames.stream()
                    .filter(name -> !foundNames.contains(name))
                    .toList();

            throw new RuntimeException("Topics not found: " + missing);
        }

        return new HashSet<>(topicsFromDb);
    }

}
