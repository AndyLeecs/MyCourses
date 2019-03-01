package com.andi.mycourses.entity;

import com.andi.mycourses.util.DateUtil;
import com.andi.mycourses.vo.HomeworkPubVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Homework {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private LocalDateTime ddl;

    private boolean hasSizeLimit;

    private int sizeLimit;

    private String[] typesLimit;

    @ManyToOne(targetEntity = LessonWhole.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="lesson_id")
    private LessonWhole lesson;

    @OneToMany(mappedBy = "homework", targetEntity = StuHomework.class, cascade = CascadeType.ALL)
    List<StuHomework> homeworks;

    public Homework(HomeworkPubVo vo, LessonWhole lesson)
    {
        this.title = vo.getTitle();
        this.content = vo.getContent();
        this.ddl = DateUtil.getLocalDateTime(vo.getDdl());
        this.hasSizeLimit = vo.isHasSizeLimit();
        this.sizeLimit = vo.getSizeLimit();
        this.typesLimit = vo.getTypesLimit();
        this.lesson = lesson;
    }
}