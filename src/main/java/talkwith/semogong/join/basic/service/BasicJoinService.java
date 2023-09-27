package talkwith.semogong.join.basic.service;

import talkwith.semogong.util.response.ResponseResult;

public interface BasicJoinService {

    ResponseResult validateFormInfo(String email, String password, String name);

    ResponseResult sendAuthEmail(String email);

    ResponseResult validateAuthCode(String email, String password, String name, String code);

}
