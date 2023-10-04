package talkwith.semogong.main.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import talkwith.semogong.main.model.PostRequestDto;
import talkwith.semogong.main.service.MainService;
import talkwith.semogong.util.response.JsonResponse;
import talkwith.semogong.util.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/main")
@Slf4j
public class MainController {

    private final MainService mainService;

    @PostMapping("/profilebar")
    public JsonResponse loadMainInfo(HttpServletRequest httpServletRequest) {

        ResponseResult result = mainService.loadMainInfo(httpServletRequest);

        return new JsonResponse(result);
    }

    @PostMapping("/load-create-post")
    public JsonResponse loadCreatePost(HttpServletRequest httpServletRequest) {

        ResponseResult result = mainService.loadCreatePost(httpServletRequest);

        return new JsonResponse(result);
    }

    @PostMapping("/create-post")
    public JsonResponse createPost(HttpServletRequest httpServletRequest, @RequestBody PostRequestDto postRequestDto) {

        ResponseResult result = mainService.createPost(httpServletRequest,postRequestDto);

        return new JsonResponse(result);
    }

    @PostMapping("/posts")
    public JsonResponse showPosts(HttpServletRequest httpServletRequest) {

        ResponseResult result = mainService.showPosts(httpServletRequest);

        return new JsonResponse(result);
    }

    @PostMapping("/post/{id}")
    public JsonResponse showPost(HttpServletRequest httpServletRequest,@PathVariable("id") Long id){
        ResponseResult result = mainService.showPost(httpServletRequest,id);

        return new JsonResponse(result);
    }


}
