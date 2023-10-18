package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.dto.RecruitDetailsResponse;
import com.wanted.preonboardingbackend.recruit.dto.RecruitListResponse;

import java.util.List;

public interface RecruitBoardCustomRepository {
    RecruitDetailsResponse findByRecruitId(String recruitId);
    List<RecruitListResponse> findAllByKeyword(String keyword);
}
