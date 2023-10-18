package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.dto.RecruitDetailsResponse;

public interface RecruitBoardCustomRepository {
    RecruitDetailsResponse findByRecruitId(String recruitId);
}
