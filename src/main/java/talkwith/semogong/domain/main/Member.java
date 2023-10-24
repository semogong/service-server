package talkwith.semogong.domain.main;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.MemberRank;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;
    private Integer totalStudyTime;
    private Integer totalStudyRatio;

    @OneToOne
    private MemberRank memberRank;

    public static Member create(String name, String email, String password) {
        Member member = new Member();
        member.name = name;
        member.email = email;
        member.password = password;

        return member;
    }
}
