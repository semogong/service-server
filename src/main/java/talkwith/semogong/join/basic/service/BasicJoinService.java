package talkwith.semogong.join.basic.service;

import org.springframework.core.codec.StringDecoder;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.exception.FormInfoException;

import java.util.HashMap;
import java.util.List;


public interface BasicJoinService {

    HashMap<String, Object> validateFormInfo(String email, String password, String name);

    void validateEmailShape(String email) throws FormInfoException;

    void validateEmailDuplicate(String email) throws FormInfoException;

    void validatePasswordLength(String password) throws FormInfoException;

    void validatePasswordShape(String password) throws FormInfoException;

    void validateNameLength(String name) throws FormInfoException;

    void validateNameShape(String name) throws FormInfoException;

    HashMap<String, Object> sendAuthEmail(String email);

    HashMap<String, Object> validateAuthCode(String email, String password, String name, String code);

//    List<Member> findMembers();
//
//    Member findOne(Long id);

}
