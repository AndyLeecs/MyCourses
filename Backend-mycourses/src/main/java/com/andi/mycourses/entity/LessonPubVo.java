package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

/**
 * @author andi
 */
@Getter
@AllArgsConstructor
public class LessonPubVo {
    private long course_id;
    private LocalDate start;
    private LocalDate end;
    private int count;
    private int limit;
    private boolean hasLimit;
}
