package talkwith.semogong.join.basic.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import talkwith.semogong.join.basic.model.JoinRequestDto;
import talkwith.semogong.join.basic.service.BasicJoinService;
import talkwith.semogong.util.response.ApiResponse;
import talkwith.semogong.util.response.StatusCode;
import talkwith.semogong.util.response.ServiceApiResponse;
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/join/basic")
@Slf4j
public class BasicJoinController {
    private final BasicJoinService basicJoinService;

    @PostMapping("/validate-form")
    public ApiResponse validateForm(@RequestBody JoinRequestDto joinRequestDto) {
        ServiceApiResponse serviceApiResponse = basicJoinService.validateForm(
                joinRequestDto.getEmail(), joinRequestDto.getPassword(), joinRequestDto.getName());

        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/send-verification-code")
    public ApiResponse sendVerificationCode(@RequestBody JoinRequestDto joinRequestDto) {
        ServiceApiResponse serviceApiResponse = basicJoinService.sendVerificationCode(joinRequestDto.getEmail());
        log.info("이메일 전송을 완료했습니다.");

        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/confirm-registration")
    public ApiResponse confirmResistration(@RequestBody JoinRequestDto joinRequestDto) {
        if (basicJoinService.validateVerification(joinRequestDto.getEmail(), joinRequestDto.getCode())) {
            ServiceApiResponse serviceApiResponse = basicJoinService.register(
                    joinRequestDto.getEmail(), joinRequestDto.getPassword(), joinRequestDto.getName());
            return new ApiResponse(serviceApiResponse);
        } else {
            return new ApiResponse(StatusCode.GENERAL_FAIL, "인증번호 불일치");
        }
    }

}
