package talkwith.semogong.session.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.SessionInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.session.repository.SessionManagerRepository;
import talkwith.semogong.session.service.SessionManagerService;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SessionManagerServiceImpl implements SessionManagerService {

    private final SessionManagerRepository sessionManagerRepository;

    @Override
    @Transactional
    public Cookie createSession(String email){
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("semogong", sessionId);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        cookie.setMaxAge(3600);

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setEmail(email);
        sessionInfo.setCookie(cookie.getValue());
        sessionManagerRepository.saveSessionInfo(sessionInfo);

        return cookie;
    }

    public Member getMemberFromSession(HttpServletRequest request){
        String cookieValue = checkSession(request);

        if (cookieValue.equals("")){
            return null;
        }

        String memberEmail = getUserEmailFromSession(cookieValue);

        Optional<Member> findMember = sessionManagerRepository.findMemberByEmail(memberEmail);

        if (findMember.isEmpty()){
            return null;
        }

        return findMember.get();

    }

    @Override
    public String checkSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String cookieValue = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("semogong".equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                    break;
                }
            }
        }

        return cookieValue;
    }

    @Override
    public String getUserEmailFromSession(String cookieValue){
        Optional<SessionInfo> sessionInfoByCookieValue = sessionManagerRepository.findSessionInfoByCookieValue(cookieValue);
        if (sessionInfoByCookieValue.isEmpty()){
            return "";
        }

        SessionInfo sessionInfo = sessionInfoByCookieValue.get();

        return sessionInfo.getEmail();
    }

}
