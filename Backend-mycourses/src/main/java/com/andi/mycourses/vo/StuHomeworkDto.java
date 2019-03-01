package com.andi.mycourses.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author andi
 */
@Getter
@AllArgsConstructor
public class StuHomeworkDto {
    String semester;
    String course;
    String student;
    String title;
}
