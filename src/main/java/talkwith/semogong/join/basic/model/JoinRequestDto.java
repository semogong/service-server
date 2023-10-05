package talkwith.semogong.join.basic.model;

import lombok.Data;

@Data
public class JoinRequestDto {
    private String email;
    private String password;
    private String name;
    private String code;
}
