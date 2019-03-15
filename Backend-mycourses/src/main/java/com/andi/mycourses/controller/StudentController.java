package com.andi.mycourses.controller;

import com.andi.mycourses.entity.Student;
import com.andi.mycourses.service.StudentService;
import com.andi.mycourses.vo.StuRegisterInfoVo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1/student")
public class StudentController {
    @Autowired
    StudentService studentService;
    @GetMapping("/info")
    public JSONObject info(HttpServletRequest request)
    {
        System.out.println("info");
        String email = (String)request.getSession().getAttribute("email");
        System.out.println(email);
        Student student = studentService.getInfo(email);
        JSONObject object = new JSONObject();
        object.put("username",student.getName());
        object.put("sid",student.getSid());
        return object;
    }
    @PostMapping("/register")
    public JSONObject register(HttpServletRequest request, @RequestBody StuRegisterInfoVo stuRegisterInfoVo)
    {
        String email = (String)request.getSession().getAttribute("email");
        Student student = new Student(stuRegisterInfoVo, email);
        System.out.println(student);
        studentService.register(student);
        return new JSONObject();
    }
}
