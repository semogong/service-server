package talkwith.semogong.join.basic.repository;

import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import java.util.Optional;

public interface BasicJoinRepository {

    Optional<Member> findMemberByEmail(String email);

    Optional<Member> findMemberByName(String name);

    void saveEmailAuthInfo(EmailAuthInfo emailAuthInfo);

    void initEmailAuthInfo(String email);

    Optional<EmailAuthInfo> findCodeByEmail(String email);
    void saveMember(Member member);

}
