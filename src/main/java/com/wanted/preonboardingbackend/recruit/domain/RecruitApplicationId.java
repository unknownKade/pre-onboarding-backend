package com.wanted.preonboardingbackend.recruit.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class RecruitApplicationId implements Serializable {
    @Comment("채용공고_id")
    private String recruitId;

    @Comment("사용자_id")
    private String userId;
}
