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
    public JsonResponse basicJoinForm(@RequestBody JoinRequestDto joinRequestDto) {
        String email = joinRequestDto.getEmail();
        String password = joinRequestDto.getPassword();
        String name = joinRequestDto.getName();

        ResponseResult result = basicJoinService.validateFormInfo(email, password, name);

        return new JsonResponse(result);
    }

    @PostMapping("/send-code")
    public JsonResponse basicJoinAuth(@RequestBody JoinRequestDto joinRequestDto) {
        String email = joinRequestDto.getEmail();

        ResponseResult result = basicJoinService.sendAuthEmail(email);
        log.info("이메일 전송을 완료했습니다.");

        return new JsonResponse(result);
    }

    @PostMapping("/check-code")
    public JsonResponse basicJoin(@RequestBody JoinRequestDto joinRequestDto) {
        String email = joinRequestDto.getEmail();
        String password = joinRequestDto.getPassword();
        String name = joinRequestDto.getName();
        String code = joinRequestDto.getCode();

        ResponseResult result = basicJoinService.validateAuthCode(email, password, name, code);

        return new JsonResponse(result);
    }

}
