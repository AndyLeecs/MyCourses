package com.andi.mycourses.controller;

import com.andi.mycourses.entity.StuRegisterInfoVo;
import com.andi.mycourses.entity.Student;
import com.andi.mycourses.entity.TeaRegisterInfoVo;
import com.andi.mycourses.entity.Teacher;
import com.andi.mycourses.service.StudentService;
import com.andi.mycourses.service.TeacherService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1/teacher")
public class TeacherController {
    @Autowired
    TeacherService teacherService;

    @GetMapping("/info")
    public JSONObject info(HttpServletRequest request)
    {
        System.out.println("info");
        String email = (String)request.getSession().getAttribute("email");
        System.out.println(email);
        Teacher teacher = teacherService.getInfo(email);
        JSONObject object = new JSONObject();
        object.put("username",teacher.getName());
        return object;
    }

    @PostMapping("/register")
    public JSONObject register(HttpServletRequest request, @RequestBody TeaRegisterInfoVo teaRegisterInfoVo)
    {
        System.out.println("register");
        String email = (String)request.getSession().getAttribute("email");
        Teacher teacher = new Teacher(teaRegisterInfoVo, email);
        teacherService.register(teacher);
        return new JSONObject();
    }
}
