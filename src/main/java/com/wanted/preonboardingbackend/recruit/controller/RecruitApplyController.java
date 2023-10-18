package com.wanted.preonboardingbackend.recruit.controller;

import com.wanted.preonboardingbackend.recruit.dto.RecruitApplyRequest;
import com.wanted.preonboardingbackend.recruit.service.RecruitApplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/recruit/apply")
@RestController
public class RecruitApplyController {
    private final RecruitApplyService recruitApplyService;

    @PostMapping
    public void applyRecruit(@RequestBody @Valid RecruitApplyRequest request){
        recruitApplyService.createRecruitApply(request);
    }
}
