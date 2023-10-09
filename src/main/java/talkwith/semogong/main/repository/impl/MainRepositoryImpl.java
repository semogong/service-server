package talkwith.semogong.main.repository.impl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.domain.main.Post;
import talkwith.semogong.main.repository.MainRepository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

import static talkwith.semogong.domain.main.QMember.member;
import static talkwith.semogong.domain.main.QPost.post;

@Repository
@RequiredArgsConstructor
public class MainRepositoryImpl implements MainRepository {
    private final EntityManager entityManager;

    private final JPAQueryFactory jpaQueryFactory;

    @Autowired
    public MainRepositoryImpl(EntityManager entityManager) {
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
    public void savePost(Post post) {
        entityManager.persist(post);
    }

    @Override
    public Optional<List<Post>> findPostsByMember(Member member){
        List<Post> posts = jpaQueryFactory
                .selectFrom(post)
                .where(post.member.eq(member))
                .fetch();
        return Optional.ofNullable(posts);
    }

    @Override
    public Optional<Post> findPostById(Long id){
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(post)
                        .where(post.id.eq(id))
                        .fetchOne()
        );
    }
}
