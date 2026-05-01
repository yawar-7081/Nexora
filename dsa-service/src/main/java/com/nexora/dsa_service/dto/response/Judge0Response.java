package com.nexora.dsa_service.dto.response;

import lombok.Data;

@Data
public class Judge0Response {
    private String stdout;
    private String stderr;
    private String compile_output;
    private String message;
    private Status status;
    private Float time;
    private Integer memory;

    @Data
    public static class Status {
        private int id;
        private String description;
    }
}
