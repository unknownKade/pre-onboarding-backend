package com.wanted.preonboardingbackend.recruit.controller;

import com.wanted.preonboardingbackend.common.ErrorCode;
import com.wanted.preonboardingbackend.company.domain.CompanyValidationMessage;
import com.wanted.preonboardingbackend.recruit.domain.RecruitValidationMessage;
import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import com.wanted.preonboardingbackend.recruit.dto.RecruitDetailsResponse;
import com.wanted.preonboardingbackend.recruit.dto.RecruitUpdateRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecruitBoardControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/recruit/board";

    String companyId = "wanted";
    String jobPosition = "백엔드 주니어 개발자";
    int signingBonus = 1000000;
    String jobDescription = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
    String requiredSkills = "Python";

    public String initTestItem(){
        RecruitCreateRequest request = new RecruitCreateRequest(
                companyId
                , jobPosition
                , signingBonus
                , jobDescription
                ,requiredSkills
        );

        return restTemplate.postForEntity(baseUrl, request, String.class).getBody();
    }

    @Nested
    @DisplayName("채용공고 등록")
    class registerRecruitBoard{
        @DisplayName("성공")
        @Test
        public void success(){
            RecruitCreateRequest request = new RecruitCreateRequest(
                    companyId
                    , jobPosition
                    , signingBonus
                    , jobDescription
                    ,requiredSkills
            );

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, request, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                softAssertions.assertThat(responseEntity.getBody()).isNotNull();
            });
        }

        @DisplayName("회사_id 없음")
        @Test
        public void companyBlank(){
            RecruitCreateRequest recruitCreateRequest = new RecruitCreateRequest(
                    ""
                    , jobPosition
                    , signingBonus
                    , jobDescription
                    ,requiredSkills
            );

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, recruitCreateRequest, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_ACCEPTABLE.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(CompanyValidationMessage.COMPANY_BLANK);
            });
        }

        @DisplayName("글자 수 제한")
        @Test
        public void stringTooLong(){
            RecruitCreateRequest recruitCreateRequest = new RecruitCreateRequest(
                    companyId
                    , jobPosition.repeat(3)
                    , signingBonus
                    , jobDescription
                    ,requiredSkills
            );

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, recruitCreateRequest, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_ACCEPTABLE.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(RecruitValidationMessage.JOB_POSITION_LONG);
            });
        }

        @DisplayName("빈 값")
        @Test
        public void blankValue(){
            RecruitCreateRequest recruitCreateRequest = new RecruitCreateRequest(
                    companyId
                    , jobPosition
                    , signingBonus
                    , ""
                    ,requiredSkills
            );

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, recruitCreateRequest, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_ACCEPTABLE.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(RecruitValidationMessage.JOB_DESCRIPTION_BLANK);
            });
        }

        @DisplayName("숫자 0 미만")
        @Test
        public void negativeNumber(){
            RecruitCreateRequest recruitCreateRequest = new RecruitCreateRequest(
                    companyId
                    , jobPosition
                    , -1
                    , jobDescription
                    ,requiredSkills
            );

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, recruitCreateRequest, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_ACCEPTABLE.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(RecruitValidationMessage.SIGNING_BONUS_NEGATIVE);
            });
        }
    }

    @DisplayName("채용공고 수정")
    @Test
    public void update(){
        String url = baseUrl + "/" + initTestItem();

        RecruitUpdateRequest request = new RecruitUpdateRequest(
                jobPosition
                , signingBonus
                , jobDescription
                ,"Django"
        );

        ResponseEntity<Void> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(request), Void.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Nested
    @DisplayName("채용공고 삭제")
    class deleteRecruitBoard{
        @DisplayName("성공")
        @Test
        public void success() {
            String url = baseUrl + "/" + initTestItem();

            restTemplate.delete(url);

            Assertions.assertThrows(RestClientException.class , () ->restTemplate.getForEntity(url, RecruitDetailsResponse.class));
        }

        @DisplayName("없는 ID")
        @Test
        public void noRecruitBoardId() {
            String url = baseUrl + "/a";

            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE,null, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_FOUND.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(ErrorCode.NOT_FOUND.getMessage());
            });
        }
    }

    @Nested
    @DisplayName("채용공고 상세 조회")
    class readRecruitDetail{
        @DisplayName("성공")
        @Test
        public void success(){
            String url = baseUrl + "/" + initTestItem();

            ResponseEntity<RecruitDetailsResponse> responseEntity = restTemplate.getForEntity(url, RecruitDetailsResponse.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
                softAssertions.assertThat(responseEntity.getBody()).isNotNull();
            });
        }

        @DisplayName("없는 ID")
        @Test
        public void noRecruitBoardId(){
            String url = baseUrl + "/a";

            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_FOUND.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(ErrorCode.NOT_FOUND.getMessage());
            });
        }
    }

}
