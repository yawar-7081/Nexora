package com.nexora.dsa_service.service.impl;

import com.nexora.dsa_service.dto.response.Judge0Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class Judge0Service {
    private final RestTemplate restTemplate = new RestTemplate();

    public Judge0Response execute(String code, int languageId,String stdin){
        Map<String,Object> body = new HashMap<>();
        body.put("source_code",code);
        body.put("language_id",languageId);
        body.put("stdin",stdin);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String,Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Judge0Response> response =
                restTemplate.postForEntity(
                        "https://ce.judge0.com/submissions?base64_encoded=false&wait=true",
                        request,
                        Judge0Response.class
                );
        return response.getBody();
    }
}
