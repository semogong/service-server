package talkwith.semogong.join.basic.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.AuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import javax.persistence.EntityManager;
import java.util.Optional;

import static talkwith.semogong.domain.main.QMember.member;
import static talkwith.semogong.domain.QAuthInfo.authInfo;

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
    public void saveAuthInfo(AuthInfo authInfo) {
        entityManager.persist(authInfo);
    }

    @Override
    public void deleteAuthInfo(String email){
        jpaQueryFactory
            .delete(authInfo)
            .where(authInfo.to.eq(email))
            .execute();
    }

    @Override
    public Optional<AuthInfo> findAuthInfoByEmail(String email) {
        AuthInfo result = jpaQueryFactory
                .selectFrom(authInfo)
                .where(authInfo.to.eq(email))
                .orderBy(authInfo.id.desc())
                .fetchFirst();

        return Optional.ofNullable(result);
    }

    @Override
    public void saveMember(Member member) {
        entityManager.persist(member);
    }

}
