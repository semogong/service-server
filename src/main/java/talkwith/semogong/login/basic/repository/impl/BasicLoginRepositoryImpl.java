package talkwith.semogong.login.basic.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

import static talkwith.semogong.domain.main.QMember.member;

@Repository
@RequiredArgsConstructor
public class BasicLoginRepositoryImpl implements BasicLoginRepository {
    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public BasicLoginRepositoryImpl(EntityManager entityManager) {
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

}
