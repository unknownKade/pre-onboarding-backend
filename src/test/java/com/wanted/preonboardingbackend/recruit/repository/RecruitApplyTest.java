package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.domain.RecruitApplication;
import com.wanted.preonboardingbackend.recruit.dto.RecruitApplyRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest
public class RecruitApplyTest {

    @Autowired
    RecruitApplyRepository recruitApplyRepository;

    @Autowired
    RecruitBoardRepository recruitBoardRepository;

    @DisplayName("채용공고 지원")
    @Test
    public void applyRequest(){
        String recruitId = recruitBoardRepository.findAll().get(0).getRecruitId();
        String userId = "user01";

        RecruitApplication recruitApplication = RecruitApplication.create(new RecruitApplyRequest(recruitId, userId));

        recruitApplyRepository.save(recruitApplication);

        List<RecruitApplication> result = recruitApplyRepository.findAll();

        assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.get(0).getRecruitId()).isEqualTo(recruitId);
            softAssertions.assertThat(result.get(0).getUserId()).isEqualTo(userId);
        });
    }
}
