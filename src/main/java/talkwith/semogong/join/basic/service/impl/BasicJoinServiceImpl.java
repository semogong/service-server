package talkwith.semogong.join.basic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.EmailAuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.exception.FormInfoException;
import talkwith.semogong.join.basic.model.JoinRequestDto;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.util.JsonResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicJoinServiceImpl implements BasicJoinService {

    private final BasicJoinRepository basicJoinRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public HashMap<String, Object> validateFormInfo(String email, String password, String name) {
        HashMap<String, Object> result = new HashMap<>();

        try {
            validateEmailShape(email);
            validateEmailDuplicate(email);
            validatePasswordLength(password);
            validatePasswordShape(password);
            validateNameLength(name);
            validateNameDuplicate(name);
            result.put("msg","회원 정보가 정상적으로 입력되었습니다.");
            result.put("statusCode",JsonResponse.SUCCESS);
            result.put("data",null);

            return result;

        } catch (FormInfoException e){
            result.put("statusCode",e.getErrorCode());
            result.put("msg",e.getMessage());

            return result;
        }

    }

    @Override
    public void validateEmailShape(String email) throws FormInfoException {
        // 이메일 양식 확인
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            throw new FormInfoException("올바른 이메일 형식이 아닙니다.",JsonResponse.SHAPE_ERROR_EMAIL);
        }
    }

    @Override
    public void validateEmailDuplicate(String email) throws FormInfoException {
        // 이메일 중복 확인
        List<Member> findMembers = basicJoinRepository.findMemberByEmail(email);

        if (!findMembers.isEmpty()) {
            throw new FormInfoException("이미 가입된 이메일입니다.",JsonResponse.DUPLICATE_EMAIL);
        }
    }

    @Override
    public void validatePasswordLength(String password) throws FormInfoException{
        Integer passWordLength = password.length();
        // 비밀번호 길이 확인
        if (!(passWordLength>=4 && passWordLength<=12)){
            throw new FormInfoException("비밀번호는 4자~12자여야 합니다.",JsonResponse.LENGTH_ERROR_PASSWORD);
        }
    }

    @Override
    public void validatePasswordShape(String password) throws FormInfoException{
        if (!password.matches("(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}")){
            throw new FormInfoException("비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함되어야 합니다.",JsonResponse.SHAPE_ERROR_PASSWORD);
        }
    }

    @Override
    public void validateNameLength(String name) throws FormInfoException{
        Integer nameWordLength = name.length();
        // 이름 길이 확인
        if (!(nameWordLength>=2 && nameWordLength<=10)){
            throw new FormInfoException("이름은 2자~10자여야 합니다.",JsonResponse.LENGTH_ERROR_NAME);
        }
    }

    @Override
    public void validateNameDuplicate(String name) throws FormInfoException{
        List<Member> findMembers = basicJoinRepository.findMemberByName(name);

        if (!findMembers.isEmpty()) {
            throw new FormInfoException("이미 존재하는 이름입니다.",JsonResponse.DUPLICATE_NAME);
        }
    }


    @Override
    @Transactional
    public HashMap<String, Object> sendAuthEmail(String email){
        HashMap<String, Object> result = new HashMap<>();

        String code = Integer.toString(new Random().nextInt(888888) + 111111);

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

        result.put("msg","이메일로 인증번호가 전송되었습니다.");
        result.put("statusCode",JsonResponse.SUCCESS);
        result.put("data",null);

        return result;
    }

    @Override
    @Transactional
    public HashMap<String, Object> validateAuthCode(String email, String password, String name, String code){
        HashMap<String, Object> result = new HashMap<>();

        EmailAuthInfo findEmailAuthInfo = basicJoinRepository.findCodeByEmail(email);

        if (findEmailAuthInfo==null){
            result.put("msg","인증요청을 눌러주세요.");
            result.put("statusCode",JsonResponse.NOT_GENERATED_CODE);
            result.put("data",null);

        } else if (!findEmailAuthInfo.getCode().equals(code)) {
            result.put("msg","인증번호가 일치하지않습니다.");
            result.put("statusCode",JsonResponse.NOT_MATCHED_CODE);
            result.put("data",null);

        } else {
            Member member = new Member();
            member.setEmail(email);
            member.setPassword(password);
            member.setName(name);
            basicJoinRepository.saveMember(member);

            result.put("msg","인증번호가 일치합니다.");
            result.put("statusCode",JsonResponse.SUCCESS);
            result.put("data",null);
        }

        return result;
    }

}
