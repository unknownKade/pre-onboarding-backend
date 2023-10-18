package com.wanted.preonboardingbackend.recruit.domain;

import com.wanted.preonboardingbackend.common.BaseEntity;
import com.wanted.preonboardingbackend.recruit.dto.RecruitApplyRequest;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@IdClass(RecruitApplicationId.class)
public class RecruitApplication extends BaseEntity {
    @Comment("채용공고_id")
    @Id
    private String recruitId;

    @Comment("사용자_id")
    @Id
    private String userId;

    public static RecruitApplication create(RecruitApplyRequest request){
        return new RecruitApplication(request.getRecruitId(), request.getUserId());
    }
}
