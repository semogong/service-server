package talkwith.semogong.session.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import talkwith.semogong.domain.SessionInfo;

import javax.servlet.http.Cookie;


public interface SessionManagerRepository extends JpaRepository<SessionInfo, Long> {

    SessionInfo findByCookie(String cookieValue);

}
