package talkwith.semogong.login.basic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import talkwith.semogong.login.basic.model.LoginRequestDto;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.session.service.impl.SessionManagerServiceImpl;
import talkwith.semogong.util.JsonResponse;
import talkwith.semogong.util.ResponseCode;
import talkwith.semogong.util.ResponseResult;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class BasicLoginController {

    private final BasicLoginService basicLoginService;

    private final SessionManagerServiceImpl sessionManagerServiceImpl;

    @PostMapping("/check-login")
    public JsonResponse basicLoginForm(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse httpServletResponse){
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        ResponseResult result = basicLoginService.validateLoginInfo(email,password);

        // 로그인 성공, 쿠키 생성 및 할당
        if (result.getStatusCode().equals(ResponseCode.SUCCESS)){
            Cookie cookie = sessionManagerServiceImpl.createSession(email);
            httpServletResponse.addCookie(cookie);
        }

        return new JsonResponse(result);
    }


    @PostMapping("/find-id")
    public JsonResponse findId(@RequestBody LoginRequestDto loginRequestDto){
        String name = loginRequestDto.getName();

        ResponseResult result = basicLoginService.validateFindId(name);

        return new JsonResponse(result);
    }


    @PostMapping("/find-pw")
    public JsonResponse findPw(@RequestBody LoginRequestDto loginRequestDto){
        String email = loginRequestDto.getEmail();

        ResponseResult result = basicLoginService.validateFindPassword(email);

        return new JsonResponse(result);
    }
}
