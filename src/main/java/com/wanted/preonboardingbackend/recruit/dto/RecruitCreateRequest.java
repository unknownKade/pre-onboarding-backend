package com.wanted.preonboardingbackend.recruit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecruitCreateRequest {
    String companyId;
    
    String jobPosition;

    int signingBonus;

    String jobDescription;

    String requiredSkills;
}
