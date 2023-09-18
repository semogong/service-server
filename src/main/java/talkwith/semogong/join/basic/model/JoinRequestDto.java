package talkwith.semogong.join.basic.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
public class JoinRequestDto {

    private String email;
    private String password;
    private String name;
    private String code;

}
