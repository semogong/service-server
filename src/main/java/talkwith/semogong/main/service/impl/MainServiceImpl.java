package talkwith.semogong.main.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.domain.main.Post;
import talkwith.semogong.main.model.PostPreviewResponseDto;
import talkwith.semogong.main.model.PostRequestDto;
import talkwith.semogong.main.model.ProfilebarResponseDto;
import talkwith.semogong.main.repository.MainRepository;
import talkwith.semogong.main.service.MainService;
import talkwith.semogong.session.service.SessionManagerService;
import talkwith.semogong.util.response.StatusCode;
import talkwith.semogong.util.response.ServiceApiResponse;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public ServiceApiResponse viewProfile(HttpServletRequest request){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ServiceApiResponse.create(StatusCode.GENERAL_FAIL, "세션 만료 : 프로필 로딩 실패");
        }

        ProfilebarResponseDto profilebarResponseDto = new ProfilebarResponseDto();
        profilebarResponseDto.setName(member.getName());
        profilebarResponseDto.setTotalStudyTime(member.getTotalstudyTime());
        profilebarResponseDto.setTotalStudyRatio(member.getTotalstudyRatio());

        return ServiceApiResponse.create(StatusCode.SUCCESS, "프로필 로딩 성공",
                Map.of("profilebarInfo",profilebarResponseDto));
    }

    @Override
    public ServiceApiResponse createPost(HttpServletRequest request){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ServiceApiResponse.create(StatusCode.GENERAL_FAIL, "세션 만료 : 게시글 생성 창 로딩 실패");
        }

        String name = member.getName();

        return ServiceApiResponse.create(StatusCode.SUCCESS, "게시글 생성 페이지 접속",
                Map.of("clientMsg","게시글 작성 페이지가 정상적으로 로드되었습니다.",
                        "memberName",name));
    }

    @Override
    @Transactional
    public ServiceApiResponse addPost(HttpServletRequest request, PostRequestDto postRequestDto){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ServiceApiResponse.create(StatusCode.GENERAL_FAIL, "세션 만료 : 회원 조회 실패");
        }

        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setSubtitle(postRequestDto.getSubtitle());
        post.setContent(postRequestDto.getContent());
        post.setMember(member);
        post.setCreatedAt(LocalDateTime.now());

        mainRepository.savePost(post);

        return ServiceApiResponse.create(StatusCode.SUCCESS, "게시글 생성 성공");
    }

    @Override
    public ServiceApiResponse listPosts(HttpServletRequest request){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ServiceApiResponse.create(StatusCode.GENERAL_FAIL, "세션 만료 : 게시글 리스트 조회 실패");
        }

        Optional<List<Post>> findPosts = mainRepository.findPostsByMember(member);

        if (findPosts.isEmpty()){
            return ServiceApiResponse.create(StatusCode.GENERAL_FAIL, "게시글 존재하지 않음.");
        }

        List<Post> posts = findPosts.get();

        List<PostPreviewResponseDto> postPreviewResponseDtos = new ArrayList<>();

        for (Post post : posts) {
            PostPreviewResponseDto postPreviewResponseDto = new PostPreviewResponseDto();

            postPreviewResponseDto.setPostId(post.getId());
            postPreviewResponseDto.setName(member.getName());
            postPreviewResponseDto.setTitle(post.getTitle());
            postPreviewResponseDto.setSubtitle(post.getSubtitle());
            postPreviewResponseDto.setContent(post.getContent());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy-MM-dd HH:mm:ss");
            String formattedDateTime = post.getCreatedAt().format(formatter);
            postPreviewResponseDto.setFormedCreatedAt(formattedDateTime);

            postPreviewResponseDtos.add(postPreviewResponseDto);
        }

        return ServiceApiResponse.create(StatusCode.SUCCESS, "게시글 조회 성공",
                Map.of("posts",postPreviewResponseDtos));
    }

    @Override
    public ServiceApiResponse viewPost(HttpServletRequest request, Long id){
        Member member = sessionManagerService.getMemberFromSession(request);

        if (member==null){
            return ServiceApiResponse.create(StatusCode.GENERAL_FAIL, "세션 만료");
        }

        Optional<Post> findPost = mainRepository.findPostById(id);

        if (findPost.isEmpty()){
            return ServiceApiResponse.create(StatusCode.GENERAL_FAIL, "게시글 존재하지 않음.");
        }

        Post post = findPost.get();

        return ServiceApiResponse.create(StatusCode.SUCCESS, "게시글 조회 성공",
                Map.of("post",post));
    }

}
