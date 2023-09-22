package talkwith.semogong.session.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import talkwith.semogong.domain.SessionInfo;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;
import talkwith.semogong.session.repository.SessionManagerRepository;
import talkwith.semogong.session.service.SessionManagerService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionManagerServiceImpl implements SessionManagerService {

    private final SessionManagerRepository sessionManagerRepository;

    @Override
    public Cookie createSession(String email){
        // 쿠키 생성
        String sessionId = UUID.randomUUID().toString();
        Cookie cookie = new Cookie("myCookie", sessionId);
        cookie.setMaxAge(3600); // 쿠키의 만료 시간을 설정 (초 단위)

        SessionInfo sessionInfo = new SessionInfo();
        sessionInfo.setEmail(email);
        sessionInfo.setCookie(cookie.getValue());
        sessionManagerRepository.save(sessionInfo);

        return cookie;
    }

    @Override
    public String getSession(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String cookieValue = "";

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("myCookie".equals(cookie.getName())) {
                    cookieValue = cookie.getValue();
                }
            }
        }

        if (cookieValue == ""){
            return "세션이 만료되었습니다.";
        }else {
            SessionInfo sessionInfo = sessionManagerRepository.findByCookie(cookieValue);
            if (sessionInfo != null) {
                return "현재 사용자 : " + sessionInfo.getEmail();
            }
            else{
                return "접근 권한이 없습니다.";
            }
        }
    }

}
