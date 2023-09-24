package talkwith.semogong.join.basic.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasicJoinRepositoryImpl implements BasicJoinRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        Member result = entityManager.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Member> findMemberByName(String name) {
        Member result = entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getSingleResult();

        return Optional.ofNullable(result);
    }

    @Override
    public void saveEmailAuthInfo(EmailAuthInfo emailAuthInfo) {
        entityManager.persist(emailAuthInfo);
    }

    @Override
    public Optional<EmailAuthInfo> findCodeByEmail(String email) {
        EmailAuthInfo result = entityManager.createQuery(
                        "SELECT e FROM EmailAuthInfo e WHERE e.to = :email ORDER BY e.id DESC",
                        EmailAuthInfo.class
                )
                .setParameter("email", email)
                .setMaxResults(1) // 최상단 결과 하나만 가져오기
                .getSingleResult();
        return Optional.ofNullable(result);
    }

    @Override
    public void saveMember(Member member) {
        entityManager.persist(member);
    }

}
