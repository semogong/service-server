package talkwith.semogong.domain;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.main.Member;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Star {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member from;

    @ManyToOne
    private Member to;

}