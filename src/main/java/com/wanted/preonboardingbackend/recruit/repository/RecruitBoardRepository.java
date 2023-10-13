package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecruitBoardRepository extends JpaRepository<RecruitBoard, String> {
}
