package com.wanted.preonboardingbackend.recruit.controller;

import com.wanted.preonboardingbackend.common.ErrorCode;
import com.wanted.preonboardingbackend.recruit.domain.RecruitValidationMessage;
import com.wanted.preonboardingbackend.recruit.dto.RecruitApplyRequest;
import com.wanted.preonboardingbackend.user.domain.UserValidationMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RecruitApplyControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    private final String baseUrl = "http://localhost:8080/recruit/apply";

    public String getRecruitId(){
        String url = "http://localhost:8080/recruit/board";
        Map<String, String> response = (Map<String, String>) restTemplate.getForEntity(url, List.class).getBody().get(0);
        return response.get("recruitId");
    }

    @DisplayName("채용공고 지원")
    @Nested
    public class applyRecruit{

        @DisplayName("성공")
        @Test
        public void success(){
            String recruitId = getRecruitId();
            String userId = "user01";
            RecruitApplyRequest request = new RecruitApplyRequest(recruitId, userId);

            ResponseEntity<Void> responseEntity = restTemplate.postForEntity(baseUrl, request, Void.class);

            assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        }

        @DisplayName("없는 채용공고")
        @Test
        public void noRecruitId(){
            String recruitId = "a";
            String userId = "user01";
            RecruitApplyRequest request = new RecruitApplyRequest(recruitId, userId);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, request, String.class);

            assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_FOUND.getStatus());
        }

        @DisplayName("사용자 없음")
        @Test
        public void userBlank(){
            String recruitId = getRecruitId();
            String userId = "";
            RecruitApplyRequest request = new RecruitApplyRequest(recruitId, userId);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, request, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_ACCEPTABLE.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(UserValidationMessage.USER_ID_BLANK);
            });
        }

        @DisplayName("채용공고_id 없음")
        @Test
        public void recruitBlank(){
            String recruitId = "";
            String userId = "user01";
            RecruitApplyRequest request = new RecruitApplyRequest(recruitId, userId);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(baseUrl, request, String.class);

            assertSoftly(softAssertions -> {
                softAssertions.assertThat(responseEntity.getStatusCode()).isEqualTo(ErrorCode.NOT_ACCEPTABLE.getStatus());
                softAssertions.assertThat(responseEntity.getBody()).isEqualTo(RecruitValidationMessage.RECRUIT_ID_BLANK);
            });
        }
    }
}
