package talkwith.semogong.login.basic.service;

import talkwith.semogong.util.response.ServiceApiResponse;

public interface BasicLoginService {

    ServiceApiResponse validateLogin(String email, String password);

    ServiceApiResponse findEmail(String name);

    ServiceApiResponse findPassword(String email);

}
