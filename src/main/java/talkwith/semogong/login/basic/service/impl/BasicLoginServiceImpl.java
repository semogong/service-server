package talkwith.semogong.login.basic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.util.ResponseCode;
import talkwith.semogong.util.ResponseResult;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicLoginServiceImpl implements BasicLoginService {

    private final BasicLoginRepository basicLoginRepository;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public ResponseResult validateLoginInfo(String email, String password) {
        ResponseResult result;

        Optional<Member> member = basicLoginRepository.findMemberByEmail(email);

        if (Objects.isNull(member)){
            result = new ResponseResult(ResponseCode.NOT_GENERATED_CODE, "회원 정보 조회 샐패",
                    Map.of("clientMsg","존재하지 않는 계정입니다."));

        } else if (!member.get().getPassword().equals(password)) {
            result = new ResponseResult(ResponseCode.NOT_MATCHED_CODE, "패스워드 불일치",
                    Map.of("clientMsg","비밀번호가 일치하지않습니다."));

        } else {
            result = new ResponseResult(ResponseCode.SUCCESS, "로그인 성공",
                    Map.of("clientMsg","로그인 성공"));
        }

        return result;
    }


    @Override
    public ResponseResult validateFindId(String name){
        ResponseResult result;

        Optional<Member> member = basicLoginRepository.findMemberByName(name);

        if (Objects.isNull(member)){
            result = new ResponseResult(ResponseCode.NOT_GENERATED_CODE, "존재하지 않는 계정",
                    Map.of("clientMsg","매칭되는 계정을 찾을 수 없습니다."));

        }else {
            result = new ResponseResult(ResponseCode.SUCCESS, "존재하지 않는 계정",
                    Map.of("clientMsg","찾으시는 계정은 " + member.get().getEmail() + " 입니다."));

        }

        return result;
    }


    @Override
    public ResponseResult validateFindPassword(String email){
        ResponseResult result;

        Optional<Member> member = basicLoginRepository.findMemberByEmail(email);

        if (Objects.isNull(member)){
            result = new ResponseResult(ResponseCode.NOT_GENERATED_CODE, "존재하지 않는 아이디",
                    Map.of("clientMsg","매칭되는 아이디를 찾을 수 없습니다."));

        }else {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[세상의 모든 공부] 일반 회원 비밀번호 찾기");
            message.setText("세모공에 방문해주셔서 감사합니다.\n\n" + "찾으시는 계정의 비밀번호는 " + member.get().getPassword() + " 입니다." + "\n\n");
            mailSender.send(message);

            result = new ResponseResult(ResponseCode.NOT_GENERATED_CODE, "비밀번호 찾기 : 이메일 전송",
                    Map.of("clientMsg","이메일로 비밀번호를 전송했습니다."));

        }

        return result;
    }

}
