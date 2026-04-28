package com.nexora.dsa_service.service.impl;

import com.nexora.dsa_service.dto.request.SubmissionRequestDto;
import com.nexora.dsa_service.dto.response.Judge0Response;
import com.nexora.dsa_service.dto.response.SubmissionResponseDto;
import com.nexora.dsa_service.entity.*;
import com.nexora.dsa_service.entity.enums.ExecutionResultStatus;
import com.nexora.dsa_service.entity.enums.SubmissionStatus;
import com.nexora.dsa_service.repository.ExecutionResultRepository;
import com.nexora.dsa_service.repository.ProblemRepository;
import com.nexora.dsa_service.repository.SubmissionRepository;
import com.nexora.dsa_service.service.ISubmissionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
@Service
@RequiredArgsConstructor
public class SubmissionService implements ISubmissionService {

    private final ProblemRepository problemRepository;
    private final ExecutionResultRepository executionResultRepository;
    private final Judge0Service judge0Service;
    private final SubmissionRepository submissionRepository;

    @Override
    @Transactional
    public SubmissionResponseDto submitCode(SubmissionRequestDto dto) {

        Problem problem = problemRepository.findById(dto.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        ProblemLanguage langConfig = problem.getProblemLanguages()
                .stream()
                .filter(l -> l.getLanguage() == dto.getLanguage())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Language not supported"));

        // 1️⃣ Create submission
        Submission submission = new Submission();
        submission.setUserId(dto.getUserId());
        submission.setProblem(problem);
        submission.setLanguage(dto.getLanguage());
        submission.setCode(dto.getCode());
        submission.setStatus(SubmissionStatus.PENDING);
        submission = submissionRepository.save(submission);

        // 2️⃣ Build final code
        String finalCode = buildFinalCode(
                langConfig.getTemplateCode(),
                dto.getCode(),
                langConfig.getDriverCode()
        );

        // 3️⃣ Prepare test cases
        List<TestCase> testCases = problem.getTestCases()
                .stream()
                .sorted(Comparator.comparing(TestCase::getOrderIndex))
                .toList();

        String combinedInput = buildCombinedInput(testCases);

        // 4️⃣ Execute
        int languageId = dto.getLanguage().getJudge0Id();

        Judge0Response response = judge0Service.execute(finalCode, languageId, combinedInput);

        // 🔥 HANDLE ERRORS PROPERLY
        if (response.getCompile_output() != null) {
            submission.setStatus(SubmissionStatus.COMPILATION_ERROR);
            submissionRepository.save(submission);
            throw new RuntimeException("Compilation Error:\n" + response.getCompile_output());
        }

        if (response.getStdout() == null) {
            submission.setStatus(SubmissionStatus.RUNTIME_ERROR);
            submissionRepository.save(submission);
            throw new RuntimeException("Runtime Error:\n" + response.getStderr());
        }

        String[] outputs = response.getStdout().trim().split("\n");

        // 5️⃣ Evaluate
        List<ExecutionResult> results = new ArrayList<>();
        boolean allPassed = true;

        for (int i = 0; i < testCases.size(); i++) {

            TestCase tc = testCases.get(i);

            String expected = tc.getOutputData().trim();
            String actual = (i < outputs.length) ? outputs[i].trim() : "";

            ExecutionResult er = new ExecutionResult();
            er.setSubmission(submission);
            er.setTestCase(tc);
            er.setInput(tc.getInputData());
            er.setExpectedOutput(expected);
            er.setActualOutput(actual);

            if (expected.equals(actual)) {
                er.setStatus(ExecutionResultStatus.PASS);
            } else {
                er.setStatus(ExecutionResultStatus.FAIL);
                allPassed = false;
            }

            results.add(er);
        }

        executionResultRepository.saveAll(results);

        // 6️⃣ Update submission
        submission.setStatus(allPassed ? SubmissionStatus.ACCEPTED : SubmissionStatus.WRONG_ANSWER);

        // FIXED (real values)
        submission.setExecutionTime(response.getTime() != null ? (long)(response.getTime() * 1000) : null);
        submission.setMemoryUsed(response.getMemory() != null ? response.getMemory().longValue() : null);

        submissionRepository.save(submission);

        return buildResponse(submission, results);
    }

    // 🔥 CLEAN INPUT BUILDER
    private String buildCombinedInput(List<TestCase> testCases) {
        StringBuilder sb = new StringBuilder();
        sb.append(testCases.size()).append("\n");

        for (TestCase tc : testCases) {
            sb.append(tc.getInputData().trim()).append("\n");
        }

        return sb.toString();
    }

    // 🔥 SAFE CODE BUILDER
    private String buildFinalCode(String template, String userCode, String driver) {

        if (!template.contains("// USER_CODE_HERE")) {
            throw new RuntimeException("Template missing USER_CODE_HERE");
        }

        String injected = template.replace("// USER_CODE_HERE", userCode.trim());

        StringBuilder imports = new StringBuilder();
        StringBuilder driverBody = new StringBuilder();

        for (String line : driver.split("\n")) {
            if (line.trim().startsWith("import")) {
                imports.append(line).append("\n");
            } else {
                driverBody.append(line).append("\n");
            }
        }

        return imports + "\n" + injected + "\n\n" + driverBody;
    }

    private SubmissionResponseDto buildResponse(Submission submission, List<ExecutionResult> results) {

        SubmissionResponseDto res = new SubmissionResponseDto();

        res.setSubmissionId(submission.getId());
        res.setProblemId(submission.getProblem().getId());
        res.setUserId(submission.getUserId());
        res.setStatus(submission.getStatus().name());
        res.setExecutionTime(submission.getExecutionTime());
        res.setMemoryUsed(submission.getMemoryUsed());
        res.setLanguage(submission.getLanguage());

        List<SubmissionResponseDto.TestCaseResultDto> list = results.stream().map(r -> {
            SubmissionResponseDto.TestCaseResultDto dto = new SubmissionResponseDto.TestCaseResultDto();
            dto.setTestCaseId(r.getTestCase().getId());
            dto.setInput(r.getInput());
            dto.setExpectedOutput(r.getExpectedOutput());
            dto.setActualOutput(r.getActualOutput());
            dto.setStatus(r.getStatus().name());
            return dto;
        }).toList();

        res.setResults(list);

        return res;
    }
}