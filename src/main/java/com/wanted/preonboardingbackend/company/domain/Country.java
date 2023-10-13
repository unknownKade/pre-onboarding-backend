package com.wanted.preonboardingbackend.company.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
public class Country {
    @Comment("국가_id")
    @Id
    long countryId;

    @Comment("국가명")
    String name;
}
