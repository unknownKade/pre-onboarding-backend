package com.wanted.preonboardingbackend.recruit.domain;

public class RecruitValidationMessage {
    public static final String RECRUIT_ID_BLANK = "채용공고가 없습니다.";
    public static final String JOB_POSITION_BLANK = "채용 포지션을 입력해주세요.";
    public static final String JOB_DESCRIPTION_BLANK = "채용 내용을 입력해주세요.";
    public static final String REQUIRED_SKILLS_BLANK = "사용 기술을 입력해주세요";
    public static final String SIGNING_BONUS_NEGATIVE = "채용 보상금은 0 이상의 숫자여야합니다.";
    public static final String JOB_POSITION_LONG = "채용 포지션은 30자 미만으로 작성해주세요.";
}
