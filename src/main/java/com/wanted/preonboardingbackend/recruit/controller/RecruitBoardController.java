package com.wanted.preonboardingbackend.recruit.controller;

import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import com.wanted.preonboardingbackend.recruit.dto.RecruitUpdateRequest;
import com.wanted.preonboardingbackend.recruit.service.RecruitBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/recruit/board")
@RestController
public class RecruitBoardController {
    private final RecruitBoardService recruitBoardService;

    @PostMapping
    public String registerRecruitBoard(@RequestBody RecruitCreateRequest request){
        return recruitBoardService.createRecruit(request);
    }

    @PutMapping("/{recruitId}")
    public void updateRecruitBoard(@PathVariable String recruitId, @RequestBody RecruitUpdateRequest request){
        recruitBoardService.updateRecruit(recruitId, request);
    }

}