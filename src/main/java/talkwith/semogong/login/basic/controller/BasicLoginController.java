package talkwith.semogong.login.basic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import talkwith.semogong.login.basic.model.LoginRequestDto;
import talkwith.semogong.util.JsonResponse;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/login")
public class BasicLoginController {

    @GetMapping("/test")
    public JsonResponse test(@RequestParam(required = false) LoginRequestDto loginRequestDto) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatusCode("0000");
        jsonResponse.setData(new HashMap<>());
        jsonResponse.setMsg("SUCCESS");

        return jsonResponse;
    }
}
