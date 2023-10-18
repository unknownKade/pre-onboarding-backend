package com.wanted.preonboardingbackend.recruit.service;

import com.wanted.preonboardingbackend.common.DataException;
import com.wanted.preonboardingbackend.recruit.domain.RecruitApplication;
import com.wanted.preonboardingbackend.recruit.domain.RecruitApplicationId;
import com.wanted.preonboardingbackend.recruit.dto.RecruitApplyRequest;
import com.wanted.preonboardingbackend.recruit.repository.RecruitApplyRepository;
import com.wanted.preonboardingbackend.recruit.repository.RecruitBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RecruitApplyService {

    private final RecruitBoardRepository recruitBoardRepository;
    private final RecruitApplyRepository recruitApplyRepository;

    public void createRecruitApply(RecruitApplyRequest request){
        if(recruitBoardRepository.findById(request.getRecruitId()).isEmpty())
            throw new DataException.DataNotFound(request.getRecruitId());

        if(recruitApplyRepository.findById(new RecruitApplicationId(request.getRecruitId(), request.getUserId())).isPresent())
            throw new DataIntegrityViolationException(request.getRecruitId());

        recruitApplyRepository.save(RecruitApplication.create(request));
    }
}
