package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Courseware extends DBFile{
    @ManyToOne(targetEntity = Course.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="course_id")
    private Course course;
    public Courseware(DBFile dbFile, Course course)
    {
        this.fileName = dbFile.getFileName();
        this.fileType = dbFile.getFileType();
        this.data = dbFile.getData();
        this.course = course;
    }
}
