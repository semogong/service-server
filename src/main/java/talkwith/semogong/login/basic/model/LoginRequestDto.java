package talkwith.semogong.login.basic.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class LoginRequestDto {
    private String email;
    private String password;
    private String name;
    private String code;
}
