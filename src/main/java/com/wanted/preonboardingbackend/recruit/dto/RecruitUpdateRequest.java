package com.wanted.preonboardingbackend.recruit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RecruitUpdateRequest {
    String jobPosition;

    int signingBonus;

    String jobDescription;

    String requiredSkills;
}