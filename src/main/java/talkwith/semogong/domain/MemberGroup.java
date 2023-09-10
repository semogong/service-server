package talkwith.semogong.domain;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.main.Group;
import talkwith.semogong.domain.main.Member;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MemberGroup {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Group group;

}