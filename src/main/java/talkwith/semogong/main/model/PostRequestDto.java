package talkwith.semogong.main.model;

import lombok.Data;

@Data
public class PostRequestDto {
    private String title;
    private String subtitle;
    private String content;
    private String tag;
}
