package talkwith.semogong.join.basic.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import javax.persistence.EntityManager;
import java.util.Optional;

import static talkwith.semogong.domain.main.QMember.member;
import static talkwith.semogong.domain.QEmailAuthInfo.emailAuthInfo;

@Repository
@RequiredArgsConstructor
public class BasicJoinRepositoryImpl implements BasicJoinRepository {

    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BasicJoinRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        Member result = jpaQueryFactory
                .selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Member> findMemberByName(String name) {
        Member result = jpaQueryFactory
                .selectFrom(member)
                .where(member.name.eq(name))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public void saveEmailAuthInfo(EmailAuthInfo emailAuthInfo) {
        entityManager.persist(emailAuthInfo);
    }

    @Override
    public void initEmailAuthInfo(String email){
        jpaQueryFactory
            .delete(emailAuthInfo)
            .where(emailAuthInfo.to.eq(email))
            .execute();
    }

    @Override
    public Optional<EmailAuthInfo> findCodeByEmail(String email) {
        EmailAuthInfo result = jpaQueryFactory
                .selectFrom(emailAuthInfo)
                .where(emailAuthInfo.to.eq(email))
                .orderBy(emailAuthInfo.id.desc())
                .fetchFirst(); // 최상단 결과 하나만 가져오기

        return Optional.ofNullable(result);
    }

    @Override
    public void saveMember(Member member) {
        entityManager.persist(member);
    }

}
