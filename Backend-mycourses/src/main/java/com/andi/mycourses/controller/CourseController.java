package com.andi.mycourses.controller;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.service.CourseService;
import com.andi.mycourses.service.TeacherService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    @Autowired
    CourseService courseService;
    @Autowired
    TeacherService teacherService;
    @PostMapping("/new")
    public JSONObject apply(HttpServletRequest request, @RequestBody JSONObject object)
    {
        courseService.apply(SessionUtil.getSessionEmail(request),object.getString("name"));
        return new JSONObject();
    }


    /**
     *
     * @return 已审批通过，当前未发布的课程
     */
    @GetMapping("/forpub")
    public JSONArray getCoursesForPub(HttpServletRequest request)
    {
        System.out.println("forpub");
        String email = SessionUtil.getSessionEmail(request);
        System.out.println(email);
        List<Course> courses = teacherService.getCoursesForPub(email);
        System.out.println(courses);
        JSONArray array = new JSONArray();
        for (Course course : courses) {
            JSONObject object = new JSONObject();
            object.put("id",course.getId());
            object.put("name",course.getName());
            array.add(object);
        }
        System.out.println(array);
        return array;
    }
}
