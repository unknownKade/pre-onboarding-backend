package com.wanted.preonboardingbackend.recruit.domain;

import com.wanted.preonboardingbackend.common.BaseEntity;
import com.wanted.preonboardingbackend.company.domain.CompanyInfo;
import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@NoArgsConstructor
@Getter
@Entity
public class RecruitBoard extends BaseEntity {
    @Comment("채용공고_id")
    @GeneratedValue(strategy = GenerationType.UUID )
    @Id
    String recruitId;

    @Comment("회사")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "company_id")
    CompanyInfo companyInfo;

    @Comment("채용포지션")
    String jobPosition;

    @Comment("채용내용")
    String jobDescription;

    @Comment("사용기술")
    String requiredSkills;

    @Comment("채용보상금")
    int signingBonus;

    public RecruitBoard(RecruitCreateRequest createRequest) {
        companyInfo = CompanyInfo.create(createRequest.getCompanyId());
        jobPosition = createRequest.getJobPosition();
        jobDescription = createRequest.getJobDescription();
        requiredSkills = createRequest.getRequiredSkills();
        signingBonus = createRequest.getSigningBonus();
    }
}
