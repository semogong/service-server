package talkwith.semogong.main.repository;

import talkwith.semogong.domain.main.Member;
import talkwith.semogong.domain.main.Post;

import java.util.List;
import java.util.Optional;

public interface MainRepository {
    Optional<Member> findMemberByEmail(String email);

    void savePost(Post post);

    Optional<List<Post>> findPostsByMember(Member member);

    Optional<Post> findPostById(Long id);
}
