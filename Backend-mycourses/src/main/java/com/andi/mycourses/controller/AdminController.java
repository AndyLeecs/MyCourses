package com.andi.mycourses.controller;

import com.andi.mycourses.entity.Course;
import com.andi.mycourses.entity.LessonWhole;
import com.andi.mycourses.service.AdminService;
import com.andi.mycourses.util.DateUtil;
import com.andi.mycourses.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    AdminService service;
    @PostMapping("/login")
    public JSONObject login(@RequestBody JSONObject object)
    {
        String password = object.getString("password");
        boolean valid = service.login(password);
        return JsonUtil.getValidObject(valid);
    }
    @GetMapping("/new")
    public JSONArray getNewCourses()
    {
        JSONArray array = new JSONArray();
        List<Course> courses = service.getNewCourses();
        for (Course course : courses)
        {
            JSONObject object = new JSONObject();
            object.put("id",course.getId());
            object.put("name",course.getName());
            object.put("teacher",course.getTeacher().getName());
            array.add(object);
        }
        return array;
    }

    @GetMapping("/pub")
    public JSONArray getNewLessons()
    {
        JSONArray array = new JSONArray();
        List<LessonWhole> lessons = service.getNewLessons();
        for (LessonWhole lesson : lessons)
        {
            JSONObject object = new JSONObject();
            object.put("id",lesson.getId());
            object.put("name",lesson.getCourse().getName());
            object.put("teacher",lesson.getCourse().getTeacher().getName());
            object.put("start", DateUtil.getDateString(lesson.getStart_time()));
            object.put("end",DateUtil.getDateString(lesson.getEnd_time()));
            object.put("hasLimit",lesson.isHasLimit());
            object.put("limit",lesson.getLimitNum());
            object.put("count",lesson.getCount());
            array.add(object);
        }
        return array;
    }

    @PostMapping("/upvoteNew/{id}")
    public JSONObject upvoteCourse(@PathVariable long id){
       return JsonUtil.getValidObject(service.voteCourse(id,1));
    }

    @PostMapping("/downvoteNew/{id}")
    public JSONObject downvoteCourse(@PathVariable long id){
        return JsonUtil.getValidObject(service.voteCourse(id, -1));
    }

    @PostMapping("/upvotePub/{id}")
    public JSONObject upvoteLesson(@PathVariable long id){
        return JsonUtil.getValidObject(service.voteLesson(id, 1));
    }

    @PostMapping("/downvotePub/{id}")
    public JSONObject downvoteLesson(@PathVariable long id){
        return JsonUtil.getValidObject(service.voteLesson(id, -1));
    }

    @GetMapping("/stat")
    public JSONObject getStatistics(){
        JSONObject object = new JSONObject();
        object.put("teacherCount",service.countTeacher());
        object.put("studentCount",service.countStudent());
        object.put("courseCount",service.countCourse());
        return object;
    }
}
