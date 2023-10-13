package com.wanted.preonboardingbackend.recruit.domain;

import com.wanted.preonboardingbackend.common.BaseException;

public class RecruitException {
    public static class DataNotFound extends BaseException{
        public DataNotFound(String message) {
            super(message);
        }
    }
}
