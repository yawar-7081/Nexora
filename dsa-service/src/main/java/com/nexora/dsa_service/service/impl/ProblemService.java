package com.nexora.dsa_service.service.impl;

import com.nexora.dsa_service.dto.request.ProblemDashboardRequestDto;
import com.nexora.dsa_service.dto.request.ProblemRequestDto;
import com.nexora.dsa_service.dto.response.ProblemDashboardResponseDto;
import com.nexora.dsa_service.dto.response.ProblemResponseDto;
import com.nexora.dsa_service.entity.*;
import com.nexora.dsa_service.entity.UserTopicStat;
import com.nexora.dsa_service.entity.enums.ProblemDificulty;
import com.nexora.dsa_service.entity.enums.ProblemStatus;
import com.nexora.dsa_service.repository.*;
import com.nexora.dsa_service.repository.specification.ProblemSpecification;
import com.nexora.dsa_service.service.IProblemService;
import com.nexora.dsa_service.transformer.ProblemTransformer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProblemService implements IProblemService {

    private final ProblemRepository problemRepository;
    private final TopicRepository topicRepository;
    private final JdbcTemplate jdbcTemplate;
    private final SubmissionRepository submissionRepository;
    private final UserProblemRepository userProblemRepository;
    private final ExecutionResultRepository executionResultRepository;
    private final UserTopicStatRepository userTopicStatRepository;




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

    @Override
    @Transactional
    public Void deleteProblemById(String problemId) {
        try{

            log.info("Attempting to delete problem with ID: {}", problemId);
            Problem problem = problemRepository.findById(problemId).orElseThrow(() -> new RuntimeException("Problem Not Found with ID: " + problemId));

//            delete reference from problem_topic_tx
            jdbcTemplate.update("DELETE FROM problem_topic_tx WHERE problem_id = ?", problemId);

//            delete reference from problem_constraints_tx
            jdbcTemplate.update("DELETE FROM problem_constraints_tx WHERE problem_id = ?", problemId);

            List<Submission> existsSubmission = submissionRepository.findByProblemId(problemId);

            if(existsSubmission!=null){
                existsSubmission.forEach(submission -> {
                    executionResultRepository.deleteBySubmissionId(submission.getId());
                });
                submissionRepository.deleteByProblemId(problemId);
            }

            UserProblem existsUserProblem = userProblemRepository.findByProblemId(problemId);

            if(existsUserProblem != null){
                UserProblem userProblem = userProblemRepository.findByProblemId(problemId);
                UserTopicStat userTopicStat = userTopicStatRepository.findByUserId(userProblem.getUserId());
                ProblemDificulty problemDificulty = problem.getProblemDificulty();
                switch (problemDificulty){
                    case EASY -> userTopicStat.setEasySolvedCount(userTopicStat.getEasySolvedCount() - 1);
                    case MEDIUM -> userTopicStat.setMediumSolvedCount(userTopicStat.getMediumSolvedCount() - 1);
                    case HARD -> userTopicStat.setHardSolvedCount(userTopicStat.getHardSolvedCount() - 1);
                }
                userProblemRepository.deleteByProblemId(problemId);
            }

            problemRepository.delete(problem);
            log.info("Problem with ID: {} deleted successfully", problemId);
            return null;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public Page<ProblemDashboardResponseDto> getAllProblems(ProblemDashboardRequestDto problemDashboardRequestDto) {
        try{
            log.info("Service Started - getAllProblems");
            log.info("Fetching problems with filters - " +
                    "problemDifficulty - {} | searchProblemByTitle - {} | topics - {}",
                    problemDashboardRequestDto.getProblemDifficulty(),problemDashboardRequestDto.getSearchProblemByTitle(),problemDashboardRequestDto.getTopic());


            List<Topic> topics = problemDashboardRequestDto.getTopic();
            Set<ProblemDificulty> problemDifficulty = problemDashboardRequestDto.getProblemDifficulty();
            String searchTitle = problemDashboardRequestDto.getSearchProblemByTitle();
            Specification<Problem> spec = null;
            int pageNumber = (problemDashboardRequestDto.getPageNumber() != null
                    && problemDashboardRequestDto.getPageNumber() >= 0)
                    ? problemDashboardRequestDto.getPageNumber()
                    : 0;

            int pageSize = (problemDashboardRequestDto.getPageSize() != null
                    && problemDashboardRequestDto.getPageSize() > 0)
                    ? problemDashboardRequestDto.getPageSize()
                    : 10;
            if(topics != null && !topics.isEmpty()){
                spec = ProblemSpecification.hasTopics(topics);
            }

            if(problemDifficulty != null && !problemDifficulty.isEmpty()){
                if(spec==null) spec = ProblemSpecification.hasDifficulty(problemDifficulty);
                else spec = spec.and(ProblemSpecification.hasDifficulty(problemDifficulty));
            }

            if(searchTitle != null && !searchTitle.trim().isBlank()){
                if(spec==null) spec = ProblemSpecification.hasTitle(searchTitle.trim());
                else spec = spec.and(ProblemSpecification.hasTitle(searchTitle));
            }

            List<Sort.Order> orders = new ArrayList<>();

            for(ProblemDashboardRequestDto.SortingParameters sortingParameters: problemDashboardRequestDto.getSortingPerametersList()){
                Sort.Direction direction = sortingParameters.isSortDirection() ? Sort.Direction.ASC : Sort.Direction.DESC;
                Sort.Order order = new Sort.Order(direction, sortingParameters.getSortBy());
                orders.add(order);
            }


            Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(orders));

            Page<Problem> problems = problemRepository.findAll(spec,pageable);

            log.info("Problems - {}",problems);

            return problems.map(problem -> {
                ProblemDashboardResponseDto problemDashboardResponseDto = new ProblemDashboardResponseDto();
                problemDashboardResponseDto.setProblemId(problem.getId());
                problemDashboardResponseDto.setDifficulty(problem.getProblemDificulty());
                problemDashboardResponseDto.setTitle(problem.getTitle());
                return problemDashboardResponseDto;
            });
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
