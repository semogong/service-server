package talkwith.semogong.login.basic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import talkwith.semogong.login.basic.model.LoginRequestDto;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.session.service.impl.SessionManagerServiceImpl;
import talkwith.semogong.util.response.JsonResponse;
import talkwith.semogong.util.response.ResponseCode;
import talkwith.semogong.util.response.ResponseResult;
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
