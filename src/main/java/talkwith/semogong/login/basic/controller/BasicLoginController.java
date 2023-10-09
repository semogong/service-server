package talkwith.semogong.login.basic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import talkwith.semogong.login.basic.model.LoginRequestDto;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.session.service.impl.SessionManagerServiceImpl;
import talkwith.semogong.util.response.ApiResponse;
import talkwith.semogong.util.response.StatusCode;
import talkwith.semogong.util.response.ServiceApiResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/login/basic")
public class BasicLoginController {
    private final BasicLoginService basicLoginService;

    private final SessionManagerServiceImpl sessionManagerServiceImpl;

    @PostMapping("/validate-login")
    public ApiResponse validateLogin(@RequestBody LoginRequestDto loginRequestDto,
                                    HttpServletResponse httpServletResponse){

        ServiceApiResponse serviceApiResponse = basicLoginService.validateLogin(
                loginRequestDto.getEmail(),loginRequestDto.getPassword());

        if (serviceApiResponse.getStatusCode().equals(StatusCode.SUCCESS)){
            Cookie cookie = sessionManagerServiceImpl.createSession(loginRequestDto.getEmail());
            httpServletResponse.addCookie(cookie);
        }
        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/find-email")
    public ApiResponse findEmail(@RequestBody LoginRequestDto loginRequestDto){
        ServiceApiResponse serviceApiResponse = basicLoginService.findEmail(loginRequestDto.getName());
        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/find-password")
    public ApiResponse findPassword(@RequestBody LoginRequestDto loginRequestDto){
        ServiceApiResponse serviceApiResponse = basicLoginService.findPassword(loginRequestDto.getEmail());
        return new ApiResponse(serviceApiResponse);
    }
}
