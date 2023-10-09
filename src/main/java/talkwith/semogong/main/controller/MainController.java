package talkwith.semogong.main.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import talkwith.semogong.main.model.PostRequestDto;
import talkwith.semogong.main.service.MainService;
import talkwith.semogong.util.response.ApiResponse;
import talkwith.semogong.util.response.ServiceApiResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members/main")
public class MainController {

    private final MainService mainService;

    @PostMapping("/profile")
    public ApiResponse viewProfile(HttpServletRequest httpServletRequest) {
        ServiceApiResponse serviceApiResponse = mainService.viewProfile(httpServletRequest);

        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/posts/create")
    public ApiResponse createPost(HttpServletRequest httpServletRequest) {
        ServiceApiResponse serviceApiResponse = mainService.createPost(httpServletRequest);

        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/posts/add")
    public ApiResponse addPost(HttpServletRequest httpServletRequest, @RequestBody PostRequestDto postRequestDto) {
        ServiceApiResponse serviceApiResponse = mainService.addPost(httpServletRequest,postRequestDto);

        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/posts")
    public ApiResponse listPosts(HttpServletRequest httpServletRequest) {
        ServiceApiResponse serviceApiResponse = mainService.listPosts(httpServletRequest);

        return new ApiResponse(serviceApiResponse);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse viewPost(HttpServletRequest httpServletRequest, @PathVariable("id") Long id){
        ServiceApiResponse serviceApiResponse = mainService.viewPost(httpServletRequest,id);

        return new ApiResponse(serviceApiResponse);
    }


}
