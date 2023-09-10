package talkwith.semogong.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;
import talkwith.semogong.domain.main.Member;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Notify {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    private Member from;

}