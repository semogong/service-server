package talkwith.semogong.join.basic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.AuthInfo;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.join.basic.repository.BasicJoinRepository;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.util.response.StatusCode;
import talkwith.semogong.util.response.ServiceApiResponse;

import javax.validation.constraints.NotNull;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicJoinServiceImpl implements BasicJoinService {

    private final BasicJoinRepository basicJoinRepository;

    private final JavaMailSender mailSender;
    private final Random random = new Random();

    @Override
    public ServiceApiResponse validateForm(String email, String password, String name) {
        if (!validateEmailShape(email)) {
            return ServiceApiResponse.create(StatusCode.SHAPE_ERROR_EMAIL, "이메일 형식 비정상 입력");
        }

        if (!validateEmailDuplicate(email)) {
            return ServiceApiResponse.create(StatusCode.DUPLICATE_EMAIL, "이메일 중복 입력");
        }

        if (!validatePasswordLength(password)) {
            return ServiceApiResponse.create(StatusCode.LENGTH_ERROR_PASSWORD, "비밀번호 길이 비정상 입력");
        }

        if (!validatePasswordShape(password)) {
            return ServiceApiResponse.create(StatusCode.SHAPE_ERROR_PASSWORD, "비밀번호 형식 비정상 입력");
        }

        if (!validateNameLength(name)) {
            return ServiceApiResponse.create(StatusCode.LENGTH_ERROR_NAME, "이름 길이 비정상 입력");
        }

        if (!validateNameDuplicate(name)) {
            return ServiceApiResponse.create(StatusCode.DUPLICATE_NAME, "이름 중복 입력");
        }

        return ServiceApiResponse.create(StatusCode.SUCCESS, "회원 정보 정상 입력");
    }

    @Override
    @Transactional
    public ServiceApiResponse sendVerificationCode(String email){
        String code = Integer.toString(random.nextInt(888888) + 111111);

        AuthInfo authInfo = new AuthInfo();
        authInfo.setTo(email);
        authInfo.setCode(code);
        basicJoinRepository.saveAuthInfo(authInfo);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[세상의 모든 공부] 일반 회원가입 이메일 인증");
        message.setText("세모공에 방문해주셔서 감사합니다.\n\n" +
                "인증번호는 " + code + " 입니다.\n\n" +
                "인증번호를 인증코드란에 기입해주세요.");
        mailSender.send(message);

        return ServiceApiResponse.create(StatusCode.SUCCESS, "일반 회원가입 인증번호 전송 성공");
    }


    private boolean validateEmailShape(String email){
        return email.matches("^[A-Za-z0-9+_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    private boolean validateEmailDuplicate(String email) {
        Optional<Member> member = basicJoinRepository.findMemberByEmail(email);
        return member.isEmpty();
    }

    private boolean validatePasswordLength(String password) {
        int passWordLength = password.length();
        return (passWordLength>=4 && passWordLength<=12);
    }

    private boolean validatePasswordShape(String password) {
        return password.matches("(?=.*\\d)(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}");
    }

    private boolean validateNameLength(String name) {
        int nameWordLength = name.length();
        return (nameWordLength>=2 && nameWordLength<=10);
    }

    private boolean validateNameDuplicate(String name) {
        Optional<Member> member = basicJoinRepository.findMemberByName(name);
        return member.isEmpty();
    }

    @Override
    @Transactional
    public boolean validateVerification(String email, @NotNull String code){
        Optional<AuthInfo> findEmailAuthInfo = basicJoinRepository.findAuthInfoByEmail(email);
        return code.equals(findEmailAuthInfo.orElseGet(AuthInfo::new).getCode());
    }

    @Override
    @Transactional
    public ServiceApiResponse register(String email, String password, String name){
        Member member = Member.create(name, email, password);
        basicJoinRepository.saveMember(member);
        basicJoinRepository.deleteAuthInfo(member.getEmail());

        return ServiceApiResponse.create(StatusCode.SUCCESS, "회원가입 완료");

    }

}
