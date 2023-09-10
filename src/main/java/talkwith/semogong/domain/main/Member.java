package talkwith.semogong.domain.main;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.MemberRank;

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
    private MemberRank memberRank;
    private Integer totalstudyTime;
    private Integer totalstudyRatio;
}
