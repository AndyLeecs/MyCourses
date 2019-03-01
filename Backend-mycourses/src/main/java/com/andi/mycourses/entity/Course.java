package com.andi.mycourses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * 创建的课程
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int approved;//开始0，审批通过1，审批未通过-1
    @ManyToOne(targetEntity = Teacher.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="teacher_email")
    private Teacher teacher;

    @OneToMany(mappedBy = "course", targetEntity = LessonWhole.class, cascade = CascadeType.ALL)
    List<LessonWhole> lessons;

    @OneToMany(mappedBy = "course", targetEntity = Courseware.class, cascade = CascadeType.ALL)
    List<Courseware> coursewares;

    @OneToMany(mappedBy = "course", targetEntity = Topic.class, cascade = CascadeType.ALL)
    List<Topic> topics;
}
