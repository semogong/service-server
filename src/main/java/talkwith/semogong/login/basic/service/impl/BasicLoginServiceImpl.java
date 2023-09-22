package talkwith.semogong.login.basic.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.login.basic.repository.BasicLoginRepository;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.util.JsonResponse;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BasicLoginServiceImpl implements BasicLoginService {

    private final BasicLoginRepository basicLoginRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    @Transactional
    public HashMap<String, Object> validateLoginInfo(String email, String password) {
        HashMap<String, Object> result = new HashMap<>();

        Member member = basicLoginRepository.findByEmail(email);

        if (member==null){
            result.put("msg","존재하지 않는 계정입니다.");
            result.put("statusCode",JsonResponse.NOT_GENERATED_CODE);
            result.put("data",null);

        } else if (!member.getPassword().equals(password)) {
            result.put("msg","비밀번호가 일치하지않습니다.");
            result.put("statusCode",JsonResponse.NOT_MATCHED_CODE);
            result.put("data",null);

        } else {
            result.put("msg","로그인 성공!");
            result.put("statusCode",JsonResponse.SUCCESS);
            //String token = generateToken(member.getEmail());
            result.put("data",null);
        }

        return result;
    }

//    private String generateToken(String email){
//        SecureRandom secureRandom = new SecureRandom();
//        byte[] keyBytes = new byte[32];
//        secureRandom.nextBytes(keyBytes);
//        String secureKey = Base64.getEncoder().encodeToString(keyBytes);
//
//        long expirationMillis = 3600000;
//
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + expirationMillis);
//
//        String token = Jwts.builder().setSubject(email).setIssuedAt(now).setExpiration(expiration).signWith(SignatureAlgorithm.HS256,secureKey).compact();
//
//        return token;
//    }

    @Override
    public HashMap<String, Object> validateFindId(String name){
        HashMap<String, Object> result = new HashMap<>();

        Member member = basicLoginRepository.findByName(name);

        if (member==null){
            result.put("msg","매칭되는 아이디를 찾을 수 없습니다.");
            result.put("statusCode",JsonResponse.NOT_GENERATED_CODE);
            result.put("data",null);

        }else {
            result.put("msg","찾으시는 계정은 " + member.getEmail() + " 입니다.");
            result.put("statusCode",JsonResponse.SUCCESS);
            result.put("data",null);
        }

        return result;
    }

    @Override
    public HashMap<String, Object> validateFindPassword(String email){
        HashMap<String, Object> result = new HashMap<>();

        Member member = basicLoginRepository.findByEmail(email);

        if (member==null){
            result.put("msg","매칭되는 아이디를 찾을 수 없습니다.");
            result.put("statusCode",JsonResponse.NOT_GENERATED_CODE);
            result.put("data",null);

        }else {
            // 비밀번호 이메일로 전송
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[세상의 모든 공부] 일반 회원 비밀번호 찾기");
            message.setText("세모공에 방문해주셔서 감사합니다.\n\n" + "찾으시는 계정의 비밀번호는 " + member.getPassword() + " 입니다." + "\n\n");
            mailSender.send(message);

            result.put("msg","이메일로 비밀번호를 전송했습니다.");
            result.put("statusCode",JsonResponse.SUCCESS);
            result.put("data",null);
        }

        return result;
    }

}
