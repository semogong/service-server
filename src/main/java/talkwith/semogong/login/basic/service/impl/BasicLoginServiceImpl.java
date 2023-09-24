package talkwith.semogong.login.basic.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.util.JsonResponse;
import talkwith.semogong.util.ResponseResult;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicLoginServiceImpl implements BasicLoginService {

    private final BasicLoginRepository basicLoginRepository;
    private final JavaMailSender mailSender;

    @Override
    @Transactional
    public ResponseResult validateLoginInfo(String email, String password) {
        ResponseResult result = new ResponseResult();

        Member member = basicLoginRepository.findByEmail(email);

        if (member==null){
            result.setStatusCode(JsonResponse.NOT_GENERATED_CODE);
            result.setMsg("존재하지 않는 계정입니다.");
            result.setData(null);

        } else if (!member.getPassword().equals(password)) {
            result.setStatusCode(JsonResponse.NOT_MATCHED_CODE);
            result.setMsg("비밀번호가 일치하지않습니다.");
            result.setData(null);

        } else {
            result.setStatusCode(JsonResponse.SUCCESS);
            result.setMsg("로그인 성공!");
            result.setData(null);
        }

        return result;
    }


    @Override
    public ResponseResult validateFindId(String name){
        ResponseResult result = new ResponseResult();

        Member member = basicLoginRepository.findByName(name);

        if (member==null){
            result.setStatusCode(JsonResponse.NOT_GENERATED_CODE);
            result.setMsg("매칭되는 아이디를 찾을 수 없습니다.");
            result.setData(null);

        }else {
            result.setStatusCode(JsonResponse.SUCCESS);
            result.setMsg("찾으시는 계정은 " + member.getEmail() + " 입니다.");
            result.setData(null);
        }

        return result;
    }


    @Override
    public ResponseResult validateFindPassword(String email){
        ResponseResult result = new ResponseResult();

        Member member = basicLoginRepository.findByEmail(email);

        if (member==null){
            result.setStatusCode(JsonResponse.NOT_GENERATED_CODE);
            result.setMsg("매칭되는 아이디를 찾을 수 없습니다.");
            result.setData(null);

        }else {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[세상의 모든 공부] 일반 회원 비밀번호 찾기");
            message.setText("세모공에 방문해주셔서 감사합니다.\n\n" + "찾으시는 계정의 비밀번호는 " + member.getPassword() + " 입니다." + "\n\n");
            mailSender.send(message);

            result.setStatusCode(JsonResponse.SUCCESS);
            result.setMsg("이메일로 비밀번호를 전송했습니다.");
            result.setData(null);
        }

        return result;
    }

}
