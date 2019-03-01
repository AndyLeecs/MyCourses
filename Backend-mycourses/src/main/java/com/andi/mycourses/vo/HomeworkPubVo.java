package com.andi.mycourses.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * @author andi
 */
@AllArgsConstructor
@Getter
public class HomeworkPubVo {
    private long id;

    private long lesson_id;

    private String title;

    private String content;

    private String ddl;

    private boolean hasSizeLimit;

    private int sizeLimit;

    private String[] typesLimit;
}
