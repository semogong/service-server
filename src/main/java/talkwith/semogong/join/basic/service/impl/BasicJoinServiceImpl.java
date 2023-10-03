package talkwith.semogong.join.basic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.util.ResponseCode;
import talkwith.semogong.util.ResponseResult;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicJoinServiceImpl implements BasicJoinService {

    private final BasicJoinRepository basicJoinRepository;

    private final JavaMailSender mailSender;

    private final Random random = new Random();

    public ResponseResult validateFormInfo(String email, String password, String name) {
        if (!validateEmailShape(email)) {
            return ResponseResult.createResult(ResponseCode.SHAPE_ERROR_EMAIL, "회원 정보 비정상 입력", Map.of("clientMsg", "올바른 이메일 형식이 아닙니다."));
        }

        if (!validateEmailDuplicate(email)) {
            return ResponseResult.createResult(ResponseCode.DUPLICATE_EMAIL, "회원 정보 비정상 입력", Map.of("clientMsg", "이미 가입된 이메일입니다."));
        }

        if (!validatePasswordLength(password)) {
            return ResponseResult.createResult(ResponseCode.LENGTH_ERROR_PASSWORD, "회원 정보 비정상 입력", Map.of("clientMsg", "비밀번호는 4자~12자여야 합니다."));
        }

        if (!validatePasswordShape(password)) {
            return ResponseResult.createResult(ResponseCode.SHAPE_ERROR_PASSWORD, "회원 정보 비정상 입력", Map.of("clientMsg", "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함되어야 합니다."));
        }

        if (!validateNameLength(name)) {
            return ResponseResult.createResult(ResponseCode.LENGTH_ERROR_NAME, "회원 정보 비정상 입력", Map.of("clientMsg", "이름은 2자~8자여야 합니다."));
        }

        if (!validateNameDuplicate(name)) {
            return ResponseResult.createResult(ResponseCode.DUPLICATE_NAME, "회원 정보 비정상 입력", Map.of("clientMsg", "이미 존재하는 이름입니다."));
        }

        return ResponseResult.createResult(ResponseCode.SUCCESS, "회원 정보 정상 입력", Map.of("clientMsg", "회원 정보가 정상적으로 입력되었습니다."));
    }

    public boolean validateEmailShape(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validateEmailDuplicate(String email) {
        Optional<Member> member = basicJoinRepository.findMemberByEmail(email);
        return member.isEmpty();
    }

    public boolean validatePasswordLength(String password) {
        int passWordLength = password.length();
        return (passWordLength>=4 && passWordLength<=12);
    }

    public boolean validatePasswordShape(String password) {
        return password.matches("(?=.*\\d)(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}");
    }

    public boolean validateNameLength(String name) {
        int nameWordLength = name.length();
        return (nameWordLength>=2 && nameWordLength<=10);
    }

    public boolean validateNameDuplicate(String name) {
        Optional<Member> member = basicJoinRepository.findMemberByName(name);
        return member.isEmpty();
    }

    @Override
    @Transactional
    public ResponseResult sendAuthEmail(String email){
        String code = Integer.toString(random.nextInt(888888) + 111111);

        EmailAuthInfo emailAuthInfo = new EmailAuthInfo();
        emailAuthInfo.setTo(email);
        emailAuthInfo.setCode(code);
        basicJoinRepository.saveEmailAuthInfo(emailAuthInfo);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[세상의 모든 공부] 일반 회원가입 이메일 인증");
        message.setText("세모공에 방문해주셔서 감사합니다.\n\n" + "인증번호는 " + code + " 입니다." + "\n\n 인증번호를 인증코드란에 기입해주세요.");
        mailSender.send(message);

        return ResponseResult.createResult(ResponseCode.SUCCESS, "일반 회원가입 인증 번호 전송 성공",
                Map.of("clientMsg","이메일로 인증번호가 전송되었습니다."));
    }

    @Override
    @Transactional
    public ResponseResult validateAuthCode(String email, String password, String name, String code){
        Optional<EmailAuthInfo> findEmailAuthInfo = basicJoinRepository.findCodeByEmail(email);

        if (findEmailAuthInfo.isEmpty()) {
            return ResponseResult.createResult(ResponseCode.NOT_GENERATED_CODE, "인증번호가 생성되지 않음",
                    Map.of("clientMsg", "인증요청을 눌러주세요."));
        }

        EmailAuthInfo emailAuthInfo = findEmailAuthInfo.get();

        if (!emailAuthInfo.getCode().equals(code)) {
            return ResponseResult.createResult(ResponseCode.NOT_MATCHED_CODE, "인증번호가 일치하지 않음",
                    Map.of("clientMsg", "인증번호가 일치하지 않습니다."));
        }

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        basicJoinRepository.saveMember(member);

        basicJoinRepository.initEmailAuthInfo(member.getEmail());

        return ResponseResult.createResult(ResponseCode.SUCCESS, "인증번호가 일치함",
                Map.of("clientMsg", "인증번호가 일치합니다."));
    }

}
