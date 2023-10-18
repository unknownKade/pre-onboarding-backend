package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import com.wanted.preonboardingbackend.recruit.dto.RecruitCreateRequest;
import com.wanted.preonboardingbackend.recruit.dto.RecruitListResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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

    @Test
    public void findAllByKeyword(){
        initializeTestData();
        String keyword = "백엔드";
        String parameter = "%" + keyword + "%";

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RecruitListResponse> cq = cb.createQuery(RecruitListResponse.class);
        Root<RecruitBoard> recruitBoard = cq.from(RecruitBoard.class);
        List<Predicate> likeCriteria = new ArrayList<>();

        cq.select(cb.construct(RecruitListResponse.class,recruitBoard));

        if(keyword != null && !keyword.isBlank()){
            likeCriteria.add(cb.like(recruitBoard.get("companyInfo").get("name"),parameter));
            likeCriteria.add(cb.like(recruitBoard.get("jobPosition"),parameter));
            likeCriteria.add(cb.like(recruitBoard.get("jobDescription"),parameter));
            likeCriteria.add(cb.like(recruitBoard.get("requiredSkills"),parameter));
            cq.where(cb.or(likeCriteria.toArray(new Predicate[0])));
        }

        cq.orderBy(cb.desc(recruitBoard.get("updatedDate")));

        List<RecruitListResponse> result = em.createQuery(cq).getResultList();

        assertSoftly(softAssertions -> {
            result.forEach((response) -> softAssertions.assertThat(response.getJobPosition()).contains(keyword));
        });
    }
}
