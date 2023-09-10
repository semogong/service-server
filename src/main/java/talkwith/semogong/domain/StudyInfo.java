package talkwith.semogong.domain;

import lombok.Getter;
import lombok.Setter;
import talkwith.semogong.domain.main.Member;
import talkwith.semogong.domain.sub.StudyStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class StudyInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer totalTime;

    private Integer studyRatio;

    private StudyStatus studyStatus;

    @ManyToOne
    private Member member;

}