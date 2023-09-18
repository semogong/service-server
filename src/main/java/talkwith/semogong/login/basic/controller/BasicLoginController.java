package talkwith.semogong.login.basic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import talkwith.semogong.join.basic.model.JoinRequestDto;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.login.basic.model.LoginRequestDto;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.util.JsonResponse;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class BasicLoginController {

    private final BasicLoginService basicLoginService;

    @PostMapping("/check-login")
    public JsonResponse BasicLoginForm(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult error){

        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        HashMap<String, Object> result = basicLoginService.validateLoginInfo(email,password);

        JsonResponse response = new JsonResponse();
        response.setStatusCode(result.get("statusCode").toString());
        response.setMsg(result.get("msg").toString());
        response.setData((Map<String, Object>) result.get("data"));

        return response;
    }

    @PostMapping("/find-id")
    public JsonResponse FindId(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult error){

        String name = loginRequestDto.getName();

        HashMap<String, Object> result = basicLoginService.validateFindId(name);

        JsonResponse response = new JsonResponse();
        response.setStatusCode(result.get("statusCode").toString());
        response.setMsg(result.get("msg").toString());
        response.setData((Map<String, Object>) result.get("data"));

        return response;
    }

    @PostMapping("/find-pw")
    public JsonResponse FindPw(@RequestBody @Valid LoginRequestDto loginRequestDto, BindingResult error){

        String email = loginRequestDto.getEmail();

        HashMap<String, Object> result = basicLoginService.validateFindPassword(email);

        JsonResponse response = new JsonResponse();
        response.setStatusCode(result.get("statusCode").toString());
        response.setMsg(result.get("msg").toString());
        response.setData((Map<String, Object>) result.get("data"));

        return response;
    }
}
