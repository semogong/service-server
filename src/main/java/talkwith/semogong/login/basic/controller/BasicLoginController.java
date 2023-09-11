package talkwith.semogong.login.basic.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkwith.semogong.login.basic.model.LoginRequestDto;
import talkwith.semogong.login.basic.service.BasicLoginService;
import talkwith.semogong.util.JsonResponse;

import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class BasicLoginController {

    private final BasicLoginService basicLoginService;

    @GetMapping("/test")
    public JsonResponse test(@RequestParam(required = false) LoginRequestDto loginRequestDto) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatusCode(JsonResponse.SUCCESS);
        HashMap<String, Object> test = new HashMap<>();
        test.put("result", basicLoginService.test());
        jsonResponse.setData(test);
        jsonResponse.setMsg("SUCCESS");

        return jsonResponse;
    }
}
