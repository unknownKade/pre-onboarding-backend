package com.wanted.preonboardingbackend.recruit.dto;

import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RecruitDetailsResponse {
    String recruitId;

    String companyName;

    String country;

    String region;

    String jobPosition;

    long signingBonus;

    String requiredSkills;

    String jobDescription;

    List<String> moreRecruit;

    public static RecruitDetailsResponse from(RecruitBoard recruitBoard, List<String> moreRecruit){
        return new RecruitDetailsResponse(
                recruitBoard.getRecruitId()
                , recruitBoard.getCompanyInfo().getName()
                , recruitBoard.getCompanyInfo().getCountry().getName()
                , recruitBoard.getCompanyInfo().getCity().getName()
                , recruitBoard.getJobPosition()
                , recruitBoard.getSigningBonus()
                , recruitBoard.getRequiredSkills()
                , recruitBoard.getJobDescription()
                , moreRecruit
        );
    }
}
