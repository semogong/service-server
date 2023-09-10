package talkwith.semogong.domain.main;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.GroupRank;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "Team")
public class Group {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member leader;

    private String name;

    private String detail;

    private Integer maxMemberNumber;

    private Integer totalStudyTime;

    @OneToOne
    private GroupRank groupRank;

}
