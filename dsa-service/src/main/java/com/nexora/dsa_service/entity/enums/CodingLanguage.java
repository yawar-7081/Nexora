package com.nexora.dsa_service.entity.enums;

public enum CodingLanguage {

    JAVA(62),
    PYTHON(71),
    CPP(54);

    private final int judge0Id;

    CodingLanguage(int judge0Id) {
        this.judge0Id = judge0Id;
    }

    public int getJudge0Id() {
        return judge0Id;
    }
}
