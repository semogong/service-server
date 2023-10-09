package talkwith.semogong.main.model;

import lombok.Data;

import java.util.List;

@Data
public class ProfilebarResponseDto {
    private String name;
    private List<String> interest;
    private Integer totalStudyTime;
    private Integer totalStudyRatio;
}
