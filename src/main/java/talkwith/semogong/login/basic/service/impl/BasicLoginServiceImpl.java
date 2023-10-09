package talkwith.semogong.login.basic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.util.response.StatusCode;
import talkwith.semogong.util.response.ServiceApiResponse;

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
    public ServiceApiResponse validateLogin(String email, String password) {
        Optional<Member> findMember = basicLoginRepository.findMemberByEmail(email);

        if (findMember.isEmpty()) {
            return ServiceApiResponse.create(StatusCode.NOT_GENERATED_CODE, "존재하지 않는 이메일");
        }

        if (!findMember.get().getPassword().equals(password)) {
            return ServiceApiResponse.create(StatusCode.NOT_MATCHED_CODE, "패스워드 불일치");
        }

        return ServiceApiResponse.create(StatusCode.SUCCESS, "로그인 성공");
    }

    @Override
    public ServiceApiResponse findEmail(String name){
        Optional<Member> findMember = basicLoginRepository.findMemberByName(name);

        if (findMember.isEmpty()){
            return ServiceApiResponse.create(StatusCode.NOT_GENERATED_CODE, "존재하지 않는 이름");
        }

        return ServiceApiResponse.create(StatusCode.SUCCESS, "일치하는 계정 발견",
                    Map.of("data",findMember.get().getEmail()));
    }

    @Override
    public ServiceApiResponse findPassword(String email){
        Optional<Member> member = basicLoginRepository.findMemberByEmail(email);

        if (member.isEmpty()){
            return ServiceApiResponse.create(StatusCode.NOT_GENERATED_CODE, "존재하지 않는 이메일");
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("[세상의 모든 공부] 일반 회원 비밀번호 찾기");
        message.setText("세모공에 방문해주셔서 감사합니다.\n\n" + "찾으시는 계정의 비밀번호는 " + member.get().getPassword() + " 입니다." + "\n\n");
        mailSender.send(message);

        return ServiceApiResponse.create(StatusCode.SUCCESS, "비밀번호 찾기 성공");
    }

}
