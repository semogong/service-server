package talkwith.semogong.login.basic.repository.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BasicLoginRepositoryImpl implements BasicLoginRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<Member> findMemberByEmail(String email) {
        Member result = entityManager.createQuery("select m from Member m where m.email = :email", Member.class)
                .setParameter("email", email)
                .getSingleResult();

        return Optional.ofNullable(result);
    }

    @Override
    public Optional<Member> findMemberByName(String name){
        Member result = entityManager.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getSingleResult();

        return Optional.ofNullable(result);
    }

}
