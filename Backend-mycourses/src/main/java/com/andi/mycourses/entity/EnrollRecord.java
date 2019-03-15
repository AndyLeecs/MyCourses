package com.andi.mycourses.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnrollRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Student.class, optional=false)
    @JoinColumn(name="student_email")
    private Student student;

    @ManyToOne(targetEntity = LessonWhole.class, optional=false)
    @JoinColumn(name="lesson_id")
    private LessonWhole lesson;

    private int whichClass;

    private boolean dropOut = false;

    @OneToMany(mappedBy = "record", targetEntity = Score.class, cascade = CascadeType.ALL)
    private List<Score> scores;

    public EnrollRecord(Student student, LessonWhole lesson)
    {
        this.student = student;
        this.lesson = lesson;
    }

    public EnrollRecord(Student student, LessonWhole lesson, int whichClass)
    {
        this.student = student;
        this.lesson = lesson;
        this.whichClass = whichClass;
    }

    @Override
    public String toString() {
        return "id " + id + " student " + student.getName();
    }
}
