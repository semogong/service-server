package talkwith.semogong.repository;

import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MembrRepository {

    @PersistenceContext
    EntityManager entityManager;

    public Long save(Member member) {
        entityManager.persist(member);
        return member.getId();
    }

    public Member find(Long id) {
        return entityManager.find(Member.class,id);
    }
}
