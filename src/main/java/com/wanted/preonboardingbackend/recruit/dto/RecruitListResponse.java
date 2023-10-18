package com.wanted.preonboardingbackend.recruit.dto;

import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import lombok.Getter;

@Getter
public class RecruitListResponse {
    String recruitId;

    String companyName;

    String country;

    String city;

    String jobPosition;

    long signingBonus;

    String requiredSkills;

    public RecruitListResponse(RecruitBoard recruitBoard) {
        recruitId = recruitBoard.getRecruitId();
        companyName = recruitBoard.getCompanyInfo().getName();
        country = recruitBoard.getCompanyInfo().getCountry().getName();
        city = recruitBoard.getCompanyInfo().getCity().getName();
        jobPosition = recruitBoard.getJobPosition();
        signingBonus = recruitBoard.getSigningBonus();
        requiredSkills = recruitBoard.getRequiredSkills();
    }
}
