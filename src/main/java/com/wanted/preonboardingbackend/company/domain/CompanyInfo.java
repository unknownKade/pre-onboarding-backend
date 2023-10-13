package com.wanted.preonboardingbackend.company.domain;

import com.wanted.preonboardingbackend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
public class CompanyInfo extends BaseEntity {
    @Id
    @Comment("회사_id")
    String companyId;

    @Comment("회사명")
    String name;

    @Comment("국가")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="country_id")
    Country country;

    @Comment("지역")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="city_id")
    City city;

    public CompanyInfo(String companyId){
        this.companyId = companyId;
    }

    public static CompanyInfo create(String companyId){
        return new CompanyInfo(companyId);
    }
}
