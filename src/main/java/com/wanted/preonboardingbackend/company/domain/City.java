package com.wanted.preonboardingbackend.company.domain;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
public class City {

    @Comment("도시_id")
    @Id
    long cityId;

    @Comment("도시_명")
    String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    Country country;
}
