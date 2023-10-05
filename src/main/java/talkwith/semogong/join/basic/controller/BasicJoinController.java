package talkwith.semogong.join.basic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import talkwith.semogong.join.basic.model.JoinRequestDto;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.util.response.JsonResponse;
import talkwith.semogong.util.response.ResponseResult;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/join")
@Slf4j
public class BasicJoinController {
    private final BasicJoinService basicJoinService;

    @PostMapping("/check-form")
    public JsonResponse basicHandleJoinForm(@RequestBody JoinRequestDto joinRequestDto) {
        String email = joinRequestDto.getEmail();
        String password = joinRequestDto.getPassword();
        String name = joinRequestDto.getName();

        ResponseResult result = basicJoinService.handleJoinForm(email, password, name);

        return new JsonResponse(result);
    }

    @PostMapping("/send-code")
    public JsonResponse basicSendAuthCode(@RequestBody JoinRequestDto joinRequestDto) {
        String email = joinRequestDto.getEmail();

        ResponseResult result = basicJoinService.sendAuthCode(email);
        log.info("이메일 전송을 완료했습니다.");

        return new JsonResponse(result);
    }

    @PostMapping("/check-join")
    public JsonResponse basicValidateAndJoin(@RequestBody JoinRequestDto joinRequestDto) {
        String email = joinRequestDto.getEmail();
        String password = joinRequestDto.getPassword();
        String name = joinRequestDto.getName();
        String code = joinRequestDto.getCode();

        ResponseResult result = basicJoinService.validateAndJoin(email, password, name, code);

        return new JsonResponse(result);
    }

}
