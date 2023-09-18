package talkwith.semogong.join.basic.repository;

import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import java.util.List;

public interface BasicJoinRepository {

    List<Member> findMemberByEmail(String email);

    List<Member> findMemberByName(String name);

    void saveEmailAuthInfo(EmailAuthInfo emailAuthInfo);

    EmailAuthInfo findCodeByEmail(String email);
    void saveMember(Member member);

}
