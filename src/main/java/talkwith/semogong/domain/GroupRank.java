package talkwith.semogong.domain;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.sub.RankType;

import javax.persistence.*;

@Entity
@Getter @Setter
public class GroupRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RankType rankType;
}
