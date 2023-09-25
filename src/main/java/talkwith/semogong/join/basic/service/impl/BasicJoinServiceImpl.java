package talkwith.semogong.join.basic.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.exception.FormInfoException;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.util.ResponseCode;
import talkwith.semogong.util.ResponseResult;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class BasicJoinServiceImpl implements BasicJoinService {

    private final BasicJoinRepository basicJoinRepository;

    private final JavaMailSender mailSender;

    private final Random random = new Random();

    @Override
    public ResponseResult validateFormInfo(String email, String password, String name) {
        try {
            validateEmailShape(email);
            validateEmailDuplicate(email);
            validatePasswordLength(password);
            validatePasswordShape(password);
            validateNameLength(name);
            validateNameDuplicate(name);

            return new ResponseResult(ResponseCode.SUCCESS, "회원 정보 정상 입력",
                    Map.of("clientMsg","회원 정보가 정상적으로 입력되었습니다."));

        } catch (FormInfoException e){
            return new ResponseResult(e.getErrorCode(),"회원 정보 비정상 입력",
                    Map.of("clientMsg",e.getMessage()));
        }

    }

    public void validateEmailShape(String email) throws FormInfoException {
        if (!email.matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new FormInfoException("올바른 이메일 형식이 아닙니다.", ResponseCode.SHAPE_ERROR_EMAIL);
        }
    }

    public void validateEmailDuplicate(String email) throws FormInfoException {
        Optional<Member> member = basicJoinRepository.findMemberByEmail(email);

        if (!member.isEmpty()) {
            throw new FormInfoException("이미 가입된 이메일입니다.",ResponseCode.DUPLICATE_EMAIL);
        }
    }

    public void validatePasswordLength(String password) throws FormInfoException{
        int passWordLength = password.length();

        if (!(passWordLength>=4 && passWordLength<=12)){
            throw new FormInfoException("비밀번호는 2자~10자여야 합니다.",ResponseCode.LENGTH_ERROR_PASSWORD);
        }
    }

    public void validatePasswordShape(String password) throws FormInfoException{
        if (!password.matches("(?=.*\\d)(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}")){
            throw new FormInfoException("비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함되어야 합니다.",ResponseCode.SHAPE_ERROR_PASSWORD);
        }
    }

    public void validateNameLength(String name) throws FormInfoException{
        int nameWordLength = name.length();

        if (!(nameWordLength>=2 && nameWordLength<=10)){
            throw new FormInfoException("이름은 2자~10자여야 합니다.",ResponseCode.LENGTH_ERROR_NAME);
        }
    }

    public void validateNameDuplicate(String name) throws FormInfoException{
        Optional<Member> member = basicJoinRepository.findMemberByName(name);

        if (!member.isEmpty()) {
            throw new FormInfoException("이미 존재하는 이름입니다.",ResponseCode.DUPLICATE_NAME);
        }
    }

    @Override
    @Transactional
    public ResponseResult sendAuthEmail(String email){
        String code = Integer.toString(random.nextInt(888888) + 111111);

        // 이메일, 인증코드 DB에 저장
        EmailAuthInfo emailAuthInfo = new EmailAuthInfo();
        emailAuthInfo.setTo(email);
        emailAuthInfo.setCode(code);
        basicJoinRepository.saveEmailAuthInfo(emailAuthInfo);

        // 인증 이메일 전송
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[세상의 모든 공부] 일반 회원가입 이메일 인증");
        message.setText("세모공에 방문해주셔서 감사합니다.\n\n" + "인증번호는 " + code + " 입니다." + "\n\n 인증번호를 인증코드란에 기입해주세요.");
        mailSender.send(message);

        return new ResponseResult(ResponseCode.SUCCESS, "일반 회원가입 인증 번호 전송 성공",
                Map.of("clientMsg","이메일로 인증번호가 전송되었습니다."));
    }

    @Override
    @Transactional
    public ResponseResult validateAuthCode(String email, String password, String name, String code){
        Optional<EmailAuthInfo> findEmailAuthInfo = basicJoinRepository.findCodeByEmail(email);

        if (findEmailAuthInfo.isEmpty()) {
            return new ResponseResult(ResponseCode.NOT_GENERATED_CODE, "인증번호가 생성되지 않음",
                    Map.of("clientMsg", "인증요청을 눌러주세요."));
        }

        EmailAuthInfo emailAuthInfo = findEmailAuthInfo.get();

        if (!emailAuthInfo.getCode().equals(code)) {
            return new ResponseResult(ResponseCode.NOT_MATCHED_CODE, "인증번호가 일치하지 않음",
                    Map.of("clientMsg", "인증번호가 일치하지 않습니다."));
        }

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        basicJoinRepository.saveMember(member);

        basicJoinRepository.initEmailAuthInfo(member.getEmail());

        return new ResponseResult(ResponseCode.SUCCESS, "인증번호가 일치함",
                Map.of("clientMsg", "인증번호가 일치합니다."));
    }

}
