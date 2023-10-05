package talkwith.semogong.main.service;

import talkwith.semogong.main.model.PostRequestDto;
import talkwith.semogong.util.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;

public interface MainService {

    ResponseResult loadMainInfo(HttpServletRequest httpServletRequest);

    ResponseResult loadCreatePost(HttpServletRequest httpServletRequest);
    ResponseResult createPost(HttpServletRequest httpServletRequest, PostRequestDto postRequestDto);

    ResponseResult showPosts(HttpServletRequest httpServletRequest);

    ResponseResult showPost(HttpServletRequest request, Long id);
}
