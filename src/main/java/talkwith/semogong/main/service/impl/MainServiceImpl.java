package talkwith.semogong.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.domain.main.Post;
import talkwith.semogong.main.model.PostRequestDto;
import talkwith.semogong.main.repository.MainRepository;
import talkwith.semogong.main.service.MainService;
import talkwith.semogong.session.service.SessionManagerService;
import talkwith.semogong.util.response.ResponseCode;
import talkwith.semogong.util.response.ResponseResult;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainServiceImpl implements MainService {
    private final SessionManagerService sessionManagerService;

    private final MainRepository mainRepository;

    @Override
    public ResponseResult loadMainInfo(HttpServletRequest request){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ResponseResult.createResult(ResponseCode.GENERAL_FAIL, "세션 만료",
                    Map.of("clientMsg","세션이 만료되었습니다.",
                            "memberName",""));
        }

        String name = member.getName();

        return ResponseResult.createResult(ResponseCode.SUCCESS, "메인화면 로딩 성공",
                Map.of("clientMsg","메인화면 로딩에 성공했습니다.",
                        "memberName",name));
    }

    @Override
    public ResponseResult loadCreatePost(HttpServletRequest request){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ResponseResult.createResult(ResponseCode.GENERAL_FAIL, "세션 만료",
                    Map.of("clientMsg","세션이 만료되었습니다.",
                            "memberName",""));
        }

        String name = member.getName();

        return ResponseResult.createResult(ResponseCode.SUCCESS, "게시글 생성 페이지 접속",
                Map.of("clientMsg","게시글 작성 페이지가 정상적으로 로드되었습니다.",
                        "memberName",name));
    }

    @Override
    @Transactional
    public ResponseResult createPost(HttpServletRequest request, PostRequestDto postRequestDto){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ResponseResult.createResult(ResponseCode.GENERAL_FAIL, "세션 만료",
                    Map.of("clientMsg","세션이 만료되었습니다.",
                            "memberName",""));
        }

        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setSubtitle(postRequestDto.getSubtitle());
        post.setContent(postRequestDto.getContent());
        post.setTag(postRequestDto.getTag());
        post.setMember(member);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        post.setFormedCreatedAt(formattedDateTime);

        mainRepository.savePost(post);

        return ResponseResult.createResult(ResponseCode.SUCCESS, "게시글 생성 성공",
                Map.of("clientMsg","게시글이 정상적으로 생성되었습니다."));
    }

    @Override
    public ResponseResult showPosts(HttpServletRequest request){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ResponseResult.createResult(ResponseCode.GENERAL_FAIL, "세션 만료",
                    Map.of("clientMsg","세션이 만료되었습니다.",
                            "memberName",""));
        }

        Optional<List<Post>> findPosts = mainRepository.findPostsByMember(member);

        if (findPosts.isEmpty()){
            return ResponseResult.createResult(ResponseCode.GENERAL_FAIL, "게시글 존재하지 않음.",
                    Map.of("clientMsg","게시글이 존재하지 않습니다."));
        }

        List<Post> posts = findPosts.get();

        return ResponseResult.createResult(ResponseCode.SUCCESS, "게시글 조회 성공",
                Map.of("clientMsg","게시글이 정상적으로 조회되었습니다.",
                        "posts",posts));

    }

    @Override
    public ResponseResult showPost(HttpServletRequest request, Long id){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ResponseResult.createResult(ResponseCode.GENERAL_FAIL, "세션 만료",
                    Map.of("clientMsg","세션이 만료되었습니다.",
                            "memberName",""));
        }

        Optional<Post> findPost = mainRepository.findPostById(id);

        if (findPost.isEmpty()){
            return ResponseResult.createResult(ResponseCode.GENERAL_FAIL, "게시글 존재하지 않음.",
                    Map.of("clientMsg","게시글이 존재하지 않습니다."));
        }

        Post post = findPost.get();

        return ResponseResult.createResult(ResponseCode.SUCCESS, "게시글 조회 성공",
                Map.of("clientMsg","게시글이 정상적으로 조회되었습니다.",
                        "post",post));

    }

}
