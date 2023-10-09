package talkwith.semogong.main.service;

import talkwith.semogong.main.model.PostRequestDto;
import talkwith.semogong.util.response.ServiceApiResponse;

import javax.servlet.http.HttpServletRequest;

public interface MainService {

    ServiceApiResponse viewProfile(HttpServletRequest httpServletRequest);

    ServiceApiResponse createPost(HttpServletRequest httpServletRequest);
    ServiceApiResponse addPost(HttpServletRequest httpServletRequest, PostRequestDto postRequestDto);

    ServiceApiResponse listPosts(HttpServletRequest httpServletRequest);

    ServiceApiResponse viewPost(HttpServletRequest request, Long id);
}
