package talkwith.semogong.login.basic.service;

import talkwith.semogong.util.response.ResponseResult;

public interface BasicLoginService {

    ResponseResult validateLoginInfo(String email, String password);

    ResponseResult validateFindId(String name);

    ResponseResult validateFindPassword(String email);

}
