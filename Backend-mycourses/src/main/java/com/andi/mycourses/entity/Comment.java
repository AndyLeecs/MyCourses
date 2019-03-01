package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(targetEntity = Comment.class, cascade= CascadeType.ALL)
    @JoinColumn(name="reply_to_id", nullable=true)
    private Comment replyTo;

    @OneToMany(mappedBy = "replyTo", targetEntity = Comment.class, cascade = CascadeType.ALL)
    private List<Comment> childComments;

    @ManyToOne(targetEntity = BaseUser.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="writer_id")
    private BaseUser writer;

    @ManyToOne(targetEntity = Topic.class, cascade= CascadeType.ALL, optional=false)
    @JoinColumn(name="topic_id")
    private Topic topic;

    private String content;

    private LocalDateTime time;

    public Comment(Comment replyTo, BaseUser writer, Topic topic, String content, LocalDateTime time)
    {
        if (replyTo != null)
        this.replyTo = replyTo;
        this.writer = writer;
        this.topic = topic;
        this.content = content;
        this.time = time;
    }
}
