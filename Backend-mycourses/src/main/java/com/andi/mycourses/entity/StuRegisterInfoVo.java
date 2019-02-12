package com.andi.mycourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author andi
 */
@Getter
@Setter
@AllArgsConstructor
public class StuRegisterInfoVo {
    private Long sid;
    private String name;
    private String password;
}
