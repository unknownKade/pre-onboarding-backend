package com.wanted.preonboardingbackend.recruit.service;

import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import com.wanted.preonboardingbackend.common.DataException;
import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import com.wanted.preonboardingbackend.recruit.dto.RecruitUpdateRequest;
import com.wanted.preonboardingbackend.recruit.repository.RecruitBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class RecruitBoardService {

    private final RecruitBoardRepository recruitBoardRepository;

    public String createRecruit(RecruitCreateRequest request){
        return recruitBoardRepository.save(new RecruitBoard(request)).getRecruitId();
    }

    public void updateRecruit(String recruitId, RecruitUpdateRequest request){
        RecruitBoard recruitBoard = recruitBoardRepository.findById(recruitId)
                .orElseThrow(() -> new DataException.DataNotFound(recruitId));
        recruitBoard.update(request);
    }
}
