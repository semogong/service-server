package talkwith.semogong.join.basic.service;

import talkwith.semogong.join.basic.exception.FormInfoException;
import talkwith.semogong.util.ResponseResult;

public interface BasicJoinService {

    ResponseResult validateFormInfo(String email, String password, String name);

    void validateEmailShape(String email) throws FormInfoException;

    void validateEmailDuplicate(String email) throws FormInfoException;

    void validatePasswordLength(String password) throws FormInfoException;

    void validatePasswordShape(String password) throws FormInfoException;

    void validateNameLength(String name) throws FormInfoException;

    void validateNameDuplicate(String name) throws FormInfoException;


    ResponseResult sendAuthEmail(String email);

    ResponseResult validateAuthCode(String email, String password, String name, String code);

}
