package talkwith.semogong.join.basic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import talkwith.semogong.join.basic.model.JoinRequestDto;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.util.JsonResponse;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
public class BasicJoinController {

    private final BasicJoinService basicJoinService;

    @PostMapping("/check-form")
    public JsonResponse BasicJoinForm(@RequestBody @Valid JoinRequestDto joinRequestDto, BindingResult error){

        String email = joinRequestDto.getEmail();
        String password = joinRequestDto.getPassword();
        String name = joinRequestDto.getName();

        HashMap<String, Object> result = basicJoinService.validateFormInfo(email,password,name);

        JsonResponse response = new JsonResponse();
        response.setStatusCode(result.get("statusCode").toString());
        response.setMsg(result.get("msg").toString());
        response.setData((Map<String, Object>) result.get("data"));

        return response;
    }

    @PostMapping("/send-code")
    public JsonResponse BasicJoinAuth(@RequestBody @Valid JoinRequestDto joinRequestDto, BindingResult error){

        String email = joinRequestDto.getEmail();

        HashMap<String, Object> result = basicJoinService.sendAuthEmail(email);

        System.out.println("이메일 전송을 완료했습니다.");

        JsonResponse response = new JsonResponse();
        response.setStatusCode(result.get("statusCode").toString());
        response.setMsg(result.get("msg").toString());
        response.setData((Map<String, Object>) result.get("data"));

        return response;
    }

    @PostMapping("/check-code")
    public JsonResponse BasicJoin(@RequestBody JoinRequestDto joinRequestDto, BindingResult error){

        String email = joinRequestDto.getEmail();
        String password = joinRequestDto.getPassword();
        String name = joinRequestDto.getName();
        String code = joinRequestDto.getCode();

        HashMap<String, Object> result = basicJoinService.validateAuthCode(email,password,name,code);

        JsonResponse response = new JsonResponse();
        response.setStatusCode(result.get("statusCode").toString());
        response.setMsg(result.get("msg").toString());
        response.setData((Map<String, Object>) result.get("data"));

        return response;
    }

}
