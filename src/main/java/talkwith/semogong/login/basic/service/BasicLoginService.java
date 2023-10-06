package talkwith.semogong.login.basic.service;

import talkwith.semogong.util.response.ResponseResult;

public interface BasicLoginService {

    ResponseResult validateLogin(String email, String password);

    ResponseResult validateFindId(String name);

    ResponseResult validateFindPassword(String email);

}
