package com.wanted.preonboardingbackend.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import org.hibernate.annotations.Comment;

@Getter
@Entity
public class UserInfo {
    @Comment("사용자_id")
    @Id
    String id;

    @Comment("이름")
    String name;

    @Comment("이메일")
    String email;
}
