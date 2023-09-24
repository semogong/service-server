package talkwith.semogong.join.basic.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BasicJoinRepositoryImpl implements BasicJoinRepository {

    private final EntityManager entityManager;

    @Override
    public List<Member> findMemberByEmail(String email){
        return entityManager.createQuery("select m from Member m where m.email = :email",
                        Member.class)
                .setParameter("email", email)
                .getResultList();
    }

    @Override
    public List<Member> findMemberByName(String name) {
        return entityManager.createQuery("select m from Member m where m.name = :name",
                        Member.class)
                .setParameter("name", name)
                .getResultList();
    }

    @Override
    public void saveEmailAuthInfo(EmailAuthInfo emailAuthInfo) {
        entityManager.persist(emailAuthInfo);
    }

    @Override
    public EmailAuthInfo findCodeByEmail(String email) {
        try {
            return entityManager.createQuery(
                            "SELECT e FROM EmailAuthInfo e WHERE e.to = :email ORDER BY e.id DESC",
                            EmailAuthInfo.class
                    )
                    .setParameter("email", email)
                    .setMaxResults(1) // 최상단 결과 하나만 가져오기
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void saveMember(Member member) {
        entityManager.persist(member);
    }

}
