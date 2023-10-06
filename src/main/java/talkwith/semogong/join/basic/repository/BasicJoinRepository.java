package talkwith.semogong.join.basic.repository;

import talkwith.semogong.domain.AuthInfo;
import talkwith.semogong.domain.main.Member;
import java.util.Optional;

public interface BasicJoinRepository {
    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByName(String name);

    void saveAuthInfo(AuthInfo authInfo);

    void initAuthInfo(String email);

    Optional<AuthInfo> findAuthInfoByEmail(String email);
    void saveMember(Member member);

}
