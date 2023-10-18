package com.wanted.preonboardingbackend.recruit.dto;

import com.wanted.preonboardingbackend.user.domain.UserValidationMessage;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RecruitApplyRequest {
    String recruitId;

    @NotBlank(message= UserValidationMessage.USER_ID_BLANK)
    String userId;
}
