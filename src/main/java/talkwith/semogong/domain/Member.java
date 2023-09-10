package talkwith.semogong.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToOne
    private UserRank userRank;
    private Integer totalstudyTime;
    private Integer totalstudyRatio;
}
