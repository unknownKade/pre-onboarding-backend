package com.wanted.preonboardingbackend.recruit.repository;

import com.wanted.preonboardingbackend.common.DataException;
import com.wanted.preonboardingbackend.recruit.domain.RecruitBoard;
import com.wanted.preonboardingbackend.recruit.dto.RecruitDetailsResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RecruitBoardRepositoryImpl implements RecruitBoardCustomRepository{

    private final EntityManager em;

    @Override
    public RecruitDetailsResponse findByRecruitId(String recruitId) {
        RecruitBoard recruitBoard = getRecruitBoard(recruitId)
                .orElseThrow(() -> new DataException.DataNotFound(recruitId));
        List<String> moreRecruit = getMoreRecruit(recruitBoard.getCompanyInfo().getCompanyId());

        return RecruitDetailsResponse.from(recruitBoard, moreRecruit);
    }

    private Optional<RecruitBoard> getRecruitBoard(String recruitId){
        Optional<RecruitBoard> recruitBoard;

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<RecruitBoard> cq = cb.createQuery(RecruitBoard.class);
        Root<RecruitBoard> r = cq.from(RecruitBoard.class);

        Fetch<String, String> companyInfo = r.fetch("companyInfo", JoinType.LEFT);
        companyInfo.fetch("country", JoinType.LEFT);
        companyInfo.fetch("city", JoinType.LEFT);

        Predicate recruitIdEqual = cb.equal(r.get("recruitId"), (recruitId));

        cq.select(r).where(recruitIdEqual);

        try{
            recruitBoard = Optional.ofNullable(em.createQuery(cq).getSingleResult());
        }catch (NoResultException e){
            throw new DataException.DataNotFound(recruitId);
        }

        return  recruitBoard;
    }

    private List<String> getMoreRecruit(String companyId){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<String> cq = cb.createQuery(String.class);
        Root<RecruitBoard> recruitBoard = cq.from(RecruitBoard.class);

        Predicate companyIdEqual = cb.equal(recruitBoard.get("companyInfo").get("companyId"),companyId);

        cq.select(recruitBoard.get("recruitId")).where(companyIdEqual);

        return em.createQuery(cq).getResultList();
    }
}
