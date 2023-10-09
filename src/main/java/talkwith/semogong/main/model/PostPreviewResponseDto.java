package talkwith.semogong.main.model;

import lombok.Data;

import java.util.List;

@Data
public class PostPreviewResponseDto {

    private Long postId;
    private String name;
    private String formedCreatedAt;
    private String title;
    private String subtitle;
    private List<String> tag;
    private String content;

}
