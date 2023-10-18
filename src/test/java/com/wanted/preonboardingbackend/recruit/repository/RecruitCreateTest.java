package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class RecruitCreateTest {
    @Autowired
    RecruitBoardRepository recruitBoardRepository;

    @DisplayName("채용공고 등록")
    @Test
    public void createRecruit(){
        //given
        String companyId = "wanted";
        String jobPosition = "백엔드 주니어 개발자";
        int signingBonus = 1000000;
        String jobDescription = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
        String requiredSkills = "Python";

        RecruitCreateRequest recruitCreateRequest = new RecruitCreateRequest(
                companyId
                , jobPosition
                , signingBonus
                , jobDescription
                ,requiredSkills
        );

        //when
        RecruitBoard result = recruitBoardRepository.save(new RecruitBoard(recruitCreateRequest));
        recruitBoardRepository.flush();

        //then
        assertSoftly(softAssertions -> {
            softAssertions.assertThat(result.getRecruitId()).isNotBlank();
            softAssertions.assertThat(result.getCompanyInfo()).isNotNull();
            softAssertions.assertThat(result.getJobPosition()).isEqualTo(jobPosition);
            softAssertions.assertThat(result.getSigningBonus()).isEqualTo(signingBonus);
            softAssertions.assertThat(result.getRequiredSkills()).isEqualTo(requiredSkills);
            softAssertions.assertThat(result.getJobDescription()).isEqualTo(jobDescription);
        });
    }

    @DisplayName("채용공고 글자 수 제한 초과")
    @Test
    public void createRecruitTooLongError(){
        RecruitCreateRequest recruitCreateRequest = new RecruitCreateRequest(
                "wanted"
                , "백엔드 주니어 개발자".repeat(5)
                , 1000000
                , "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은.."
                ,"Python"
        );

        //then
        assertThrows(DataIntegrityViolationException.class,
                () -> recruitBoardRepository.save(new RecruitBoard(recruitCreateRequest)));
    }
}
