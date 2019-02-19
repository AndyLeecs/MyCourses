package com.andi.mycourses.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class StuHomework extends DBFile{

    @ManyToOne(targetEntity = Homework.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="homework_id")
    private Homework homework;

    @ManyToOne(targetEntity = Student.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="student_email")
    private Student student;

    public void rename()
    {
        String postFix = fileName.substring(fileName.lastIndexOf("."));
        this.fileName = student.getSid()+postFix;
    }

    public StuHomework(DBFile file, Homework homework, Student student)
    {
        this.homework = homework;
        this.student = student;
        this.fileName = file.getFileName();
        this.fileType = file.getFileType();
        this.data = file.getData();
    }
}
