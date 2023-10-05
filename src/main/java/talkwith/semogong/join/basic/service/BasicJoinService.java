package talkwith.semogong.join.basic.service;

import talkwith.semogong.util.response.ResponseResult;

public interface BasicJoinService {
    ResponseResult sendAuthCode(String email);
    ResponseResult handleJoinForm(String email, String password, String name);
    ResponseResult validateAndJoin(String email, String password, String name, String code);
}
