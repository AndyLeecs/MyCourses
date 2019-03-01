package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;
    private BigDecimal score;

    @ManyToOne(targetEntity = EnrollRecord.class, optional=false)
    @JoinColumn(name="record_id")
    private EnrollRecord record;

    private boolean showPublic;

    public Score(String title, BigDecimal score, EnrollRecord record, boolean showPublic)
    {
        this.title = title;
        this.score = score;
        this.record = record;
        this.showPublic = showPublic;
    }
}
