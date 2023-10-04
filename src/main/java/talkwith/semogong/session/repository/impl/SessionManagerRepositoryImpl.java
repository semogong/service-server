package talkwith.semogong.session.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.SessionInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.session.repository.SessionManagerRepository;

import javax.persistence.EntityManager;
import java.util.Optional;
import static talkwith.semogong.domain.QSessionInfo.sessionInfo;
import static talkwith.semogong.domain.main.QMember.member;

@Repository
@RequiredArgsConstructor
public class SessionManagerRepositoryImpl implements SessionManagerRepository {
    private final EntityManager entityManager;
    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public SessionManagerRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    @Override
    public void saveSessionInfo(SessionInfo sessionInfo){
        entityManager.persist(sessionInfo);
    }

    @Override
    public Optional<SessionInfo> findSessionInfoByCookieValue(String cookieValue){
        SessionInfo result = jpaQueryFactory.selectFrom(sessionInfo)
                .where(sessionInfo.cookie.eq(cookieValue))
                .fetchOne();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        Member result = jpaQueryFactory
                .selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();

        return Optional.ofNullable(result);
    }

}
