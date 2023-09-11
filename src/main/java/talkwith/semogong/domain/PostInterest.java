package talkwith.semogong.domain;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.main.Post;

import javax.persistence.*;

@Entity
@Getter @Setter
public class PostInterest {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private Interest interest;
}