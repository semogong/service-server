package talkwith.semogong.session.service;

import talkwith.semogong.domain.main.Member;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface SessionManagerService {
    Cookie createSession(String email);

    Member getMemberFromSession(HttpServletRequest request);

    String checkSession(HttpServletRequest request);

    String getUserEmailFromSession(String cookieValue);

}
