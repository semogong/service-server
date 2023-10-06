package talkwith.semogong.session.repository;

import talkwith.semogong.domain.SessionInfo;
import talkwith.semogong.domain.main.Member;

import java.util.Optional;

public interface SessionManagerRepository {

    void saveSessionInfo(SessionInfo sessionInfo);

    Optional<SessionInfo> findSessionInfoByCookieValue(String cookieValue);

    Optional<Member> findMemberByEmail(String email);

}
