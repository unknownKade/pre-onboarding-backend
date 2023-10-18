package com.wanted.preonboardingbackend.recruit.controller;

import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import com.wanted.preonboardingbackend.recruit.dto.RecruitDetailsResponse;
import com.wanted.preonboardingbackend.recruit.dto.RecruitListResponse;
import com.wanted.preonboardingbackend.recruit.dto.RecruitUpdateRequest;
import com.wanted.preonboardingbackend.recruit.service.RecruitBoardService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@RequestMapping("/recruit/board")
@RestController
public class RecruitBoardController {
    private final RecruitBoardService recruitBoardService;

    @PostMapping
    public String registerRecruitBoard(@RequestBody @Valid RecruitCreateRequest request){
        return recruitBoardService.createRecruit(request);
    }

    @PutMapping("/{recruitId}")
    public void updateRecruitBoard(@PathVariable String recruitId, @RequestBody @Valid RecruitUpdateRequest request){
        recruitBoardService.updateRecruit(recruitId, request);
    }

    @DeleteMapping("/{recruitId}")
    public void deleteRecruitBoard(@PathVariable String recruitId){
        recruitBoardService.deleteRecruit(recruitId);
    }

    @Transactional(readOnly = true)
    @GetMapping("/{recruitId}")
    public RecruitDetailsResponse readRecruitDetail(@PathVariable String recruitId){
        return recruitBoardService.readRecruitDetail(recruitId);
    }

    @Transactional(readOnly = true)
    @GetMapping
    public List<RecruitListResponse> readRecruitListDetail(@Nullable @RequestParam("search") String keyword){
        return recruitBoardService.readRecruitList(keyword);
    }
}
