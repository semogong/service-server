package talkwith.semogong.login.basic.service;

import java.util.HashMap;

public interface BasicLoginService {

    HashMap<String, Object> validateLoginInfo(String email, String password);

    HashMap<String, Object> validateFindId(String name);

    HashMap<String, Object> validateFindPassword(String email);

}
