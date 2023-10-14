package com.wanted.preonboardingbackend.common;

public class DataException {
    public static class DataNotFound extends BaseException{
        public DataNotFound(String message) {
            super(message);
        }
    }
}
