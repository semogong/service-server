package talkwith.semogong.session.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public interface SessionManagerService {
    Cookie createSession(String email);
    String getSession(HttpServletRequest request);
}
