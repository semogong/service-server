package talkwith.semogong.login.basic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.util.response.ResponseCode;
import talkwith.semogong.util.response.ResponseResult;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicLoginServiceImpl implements BasicLoginService {

    private final BasicLoginRepository basicLoginRepository;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public ResponseResult validateLogin(String email, String password) {
        Optional<Member> findMember = basicLoginRepository.findMemberByEmail(email);

        if (findMember.isEmpty()) {
            return ResponseResult.createResult(ResponseCode.NOT_GENERATED_CODE, "회원 정보 조회 샐패",
                    Map.of("clientMsg", "존재하지 않는 계정입니다."));
        }

        Member member = findMember.get();

        if (!member.getPassword().equals(password)) {
            return ResponseResult.createResult(ResponseCode.NOT_MATCHED_CODE, "패스워드 불일치",
                    Map.of("clientMsg","비밀번호가 일치하지않습니다."));
        }

        return ResponseResult.createResult(ResponseCode.SUCCESS, "로그인 성공",
                Map.of("clientMsg", "로그인 성공"));
    }

    @Override
    public ResponseResult validateFindId(String name){
        Optional<Member> findMember = basicLoginRepository.findMemberByName(name);

        if (findMember.isEmpty()){
            return ResponseResult.createResult(ResponseCode.NOT_GENERATED_CODE, "존재하지 않는 계정",
                    Map.of("clientMsg","매칭되는 계정을 찾을 수 없습니다."));
        }

        Member member = findMember.get();

        return ResponseResult.createResult(ResponseCode.SUCCESS, "일치하는 계정 발견",
                    Map.of("clientMsg","찾으시는 계정은 " + member.getEmail() + " 입니다."));
    }

    @Override
    public ResponseResult validateFindPassword(String email){
        Optional<Member> member = basicLoginRepository.findMemberByEmail(email);

        if (member.isEmpty()){
            return ResponseResult.createResult(ResponseCode.NOT_GENERATED_CODE, "존재하지 않는 아이디",
                    Map.of("clientMsg","매칭되는 아이디를 찾을 수 없습니다."));
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[세상의 모든 공부] 일반 회원 비밀번호 찾기");
        message.setText("세모공에 방문해주셔서 감사합니다.\n\n" + "찾으시는 계정의 비밀번호는 " + member.get().getPassword() + " 입니다." + "\n\n");
        mailSender.send(message);

        return ResponseResult.createResult(ResponseCode.SUCCESS, "비밀번호 찾기 : 이메일 전송",
                Map.of("clientMsg","이메일로 비밀번호를 전송했습니다."));
    }

}
