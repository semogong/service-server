package talkwith.semogong.login.basic.model;

import lombok.Data;
@Data
public class LoginRequestDto {
    private String email;
    private String password;
    private String name;
    private String code;
}
