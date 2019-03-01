package com.andi.mycourses.entity;

import com.andi.mycourses.vo.TeaRegisterInfoVo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author andi
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Teacher extends BaseUser{
    @OneToMany(mappedBy = "teacher", targetEntity = Course.class, cascade = CascadeType.ALL)
    List<Course> courses;
    public Teacher(TeaRegisterInfoVo teaRegisterInfoVo, String email)
    {
        this.email = email;
        this.name = teaRegisterInfoVo.getName();
        this.password = teaRegisterInfoVo.getPassword();
    }
}
