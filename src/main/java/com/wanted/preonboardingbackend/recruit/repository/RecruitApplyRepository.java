package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.domain.RecruitApplication;
import com.wanted.preonboardingbackend.recruit.domain.RecruitApplicationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitApplyRepository extends JpaRepository<RecruitApplication, RecruitApplicationId> {
}
