package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RecruitReadTest {

    @Autowired
    EntityManager em;

    @Autowired
    RecruitBoardRepository recruitBoardRepository;

    //given
    String companyId = "wanted";
    String jobPosition = "백엔드 주니어 개발자";
    int signingBonus = 1000000;
    String jobDescription = "원티드랩에서 백엔드 주니어 개발자를 채용합니다. 자격요건은..";
    String requiredSkills = "Python";

    public RecruitBoard initializeTestData(){
        RecruitCreateRequest request = new RecruitCreateRequest(
                companyId
                , jobPosition
                , signingBonus
                , jobDescription
                ,requiredSkills
        );

        return recruitBoardRepository.save(new RecruitBoard(request));
    }


    @Test
    public void findByRecruitId() {
        String recruitId = initializeTestData().getRecruitId();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RecruitBoard> cq = cb.createQuery(RecruitBoard.class);
        Root<RecruitBoard> recruitBoard = cq.from(RecruitBoard.class);
        Fetch<String, String> companyInfo = recruitBoard.fetch("companyInfo", JoinType.LEFT);
        companyInfo.fetch("country", JoinType.LEFT);
        companyInfo.fetch("city", JoinType.LEFT);

        cq.select(recruitBoard)
                .where(cb.equal(recruitBoard.get("recruitId"), (recruitId)));

        Optional<RecruitBoard> result = Optional.ofNullable(em.createQuery(cq).getSingleResult());

        assertThat(result).isNotNull();
    }

    @Test
    public void findAllByCompanyId(){
        String companyId = initializeTestData().getCompanyInfo().getCompanyId();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<RecruitBoard> recruitBoard = cq.from(RecruitBoard.class);

        cq.select(recruitBoard.get("recruitId"))
                .where(cb.equal(recruitBoard.get("companyInfo").get("companyId"),companyId));

        List<String> result = em.createQuery(cq).getResultList();

        result.forEach(System.out::println);
    }
}
