package com.wanted.preonboardingbackend.recruit.dto;

import com.wanted.preonboardingbackend.recruit.domain.RecruitValidationMessage;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class RecruitUpdateRequest {
    @NotBlank(message= RecruitValidationMessage.JOB_POSITION_BLANK)
    @Size(max= 20, message= RecruitValidationMessage.JOB_POSITION_LONG)
    String jobPosition;

    @Min(value = 0, message = RecruitValidationMessage.SIGNING_BONUS_NEGATIVE)
    long signingBonus;

    @NotBlank(message= RecruitValidationMessage.JOB_DESCRIPTION_BLANK)
    String jobDescription;

    @NotBlank(message= RecruitValidationMessage.REQUIRED_SKILLS_BLANK)
    String requiredSkills;
}
