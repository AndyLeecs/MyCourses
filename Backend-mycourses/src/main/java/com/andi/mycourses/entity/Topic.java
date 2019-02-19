package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.util.List;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;

    @ManyToOne(targetEntity = Course.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="course_id")
    private Course course;

    @OneToMany(mappedBy = "topic", targetEntity = Comment.class, cascade = CascadeType.ALL)
    List<Comment> comments;

    public Topic(String title, Course course)
    {
        this.title = title;
        this.course = course;
    }
}
