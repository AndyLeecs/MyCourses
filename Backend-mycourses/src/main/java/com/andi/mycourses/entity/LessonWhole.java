package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 发布的一期课程
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LessonWhole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int approved;//开始0，审批通过1，审批未通过-1
    @ManyToOne(targetEntity = Course.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="course_id")
    private Course course;
    private int count;
    private boolean hasLimit;
    private int limitNum;
    private LocalDate start_time;
    private LocalDate end_time;

    public LessonWhole(Course course, LessonPubVo vo)
    {
        this.course = course;
        this.count = vo.getCount();
        this.limitNum = vo.getLimit();
        this.hasLimit = vo.isHasLimit();
        this.start_time = vo.getStart();
        this.end_time = vo.getEnd();
    }
}
