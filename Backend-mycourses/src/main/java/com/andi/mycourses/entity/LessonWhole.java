package com.andi.mycourses.entity;

import com.andi.mycourses.vo.LessonPubVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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
    private String semester;
    private int count;//班级数
    private boolean hasLimit;
    private int limitNum;//班额
    private LocalDate start_time;
    private LocalDate end_time;


    @OneToMany(mappedBy = "lesson", targetEntity = Homework.class, cascade = CascadeType.ALL)
    private List<Homework> homeworks;

    @OneToMany(mappedBy = "lesson", targetEntity = EnrollRecord.class, cascade = CascadeType.ALL)
    private List<EnrollRecord> records;

    public LessonWhole(Course course, LessonPubVo vo)
    {
        this.course = course;
        this.count = vo.getCount();
        this.limitNum = vo.getLimit();
        this.hasLimit = vo.isHasLimit();
        this.start_time = vo.getStart();
        this.end_time = vo.getEnd();
        String[] semesterDetail = vo.getSemester();
        this.semester = semesterDetail[0]
                +"至"+semesterDetail[1]+"学年第"+semesterDetail[2]+"学期";
    }
}
