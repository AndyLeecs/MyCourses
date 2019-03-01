package com.andi.mycourses.controller;

import com.andi.mycourses.entity.EnrollRecord;
import com.andi.mycourses.entity.LessonWhole;
import com.andi.mycourses.entity.Score;
import com.andi.mycourses.entity.StuHomework;
import com.andi.mycourses.service.LessonService;
import com.andi.mycourses.vo.StuHomeworkDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1/stat")
public class StatController {
    @Autowired
    LessonService service;
    @GetMapping("/attend")
    public JSONArray getAttended(HttpServletRequest request)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> lessons = service.getAttended(email);
        JSONArray array = new JSONArray();
        for (LessonWhole lesson : lessons)
        {
            JSONObject object = new JSONObject();
            object.put("semester",lesson.getSemester());
            object.put("name",lesson.getCourse().getName());
            object.put("teacher",lesson.getCourse().getTeacher().getName());
            array.add(object);
        }
        return array;
    }

    @GetMapping("/dropout")
    public JSONArray getDropout(HttpServletRequest request)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> lessons = service.getDropout(email);
        JSONArray array = new JSONArray();
        for (LessonWhole lesson : lessons)
        {
            JSONObject object = new JSONObject();
            object.put("semester",lesson.getSemester());
            object.put("name",lesson.getCourse().getName());
            object.put("teacher",lesson.getCourse().getTeacher().getName());
            array.add(object);
        }
        return array;
    }

    @GetMapping("/score")
    public JSONArray getScores(HttpServletRequest request)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<Score> scores = service.getScores(email);
        JSONArray array = new JSONArray();
        for (Score score : scores)
        {
            JSONObject object = new JSONObject();
            LessonWhole lesson = score.getRecord().getLesson();
            object.put("semester",lesson.getSemester());
            object.put("name",lesson.getCourse().getName());
            object.put("teacher",lesson.getCourse().getTeacher().getName());
            object.put("title",score.getTitle());
            object.put("score",score.getScore());
            array.add(object);
        }
        return array;
    }

    @GetMapping("/enrollment")
    public JSONArray getEnrollments(HttpServletRequest request){
        String email = SessionUtil.getSessionEmail(request);
        List<EnrollRecord> records = service.getEnrollment(email);
        JSONArray array = new JSONArray();
        for (EnrollRecord record : records)
        {
            JSONObject object = new JSONObject();
            LessonWhole lesson = record.getLesson();
            object.put("semester",lesson.getSemester());
            object.put("name",lesson.getCourse().getName());
            object.put("student",record.getStudent().getName());
            array.add(object);
        }
        return array;
    }

    @GetMapping("/published")
    public JSONArray getAllLessons(HttpServletRequest request){
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> lessons = service.getLessons(email);
        JSONArray array = new JSONArray();
        for (LessonWhole lesson : lessons)
        {
            JSONObject object = new JSONObject();
            object.put("semester",lesson.getSemester());
            object.put("name",lesson.getCourse().getName());
            array.add(object);
        }
        return array;
    }

    @GetMapping("/work")
    public JSONArray getAllHomeworks(HttpServletRequest request)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<StuHomework> homeworks = service.getHomeworkStats(email);
        JSONArray array = new JSONArray();
        for (StuHomework homework : homeworks)
        {
            JSONObject object = new JSONObject();
            object.put("semester",homework.getHomework().getLesson().getSemester());
            object.put("name",homework.getHomework().getLesson().getCourse().getName());
            object.put("title",homework.getHomework().getTitle());
            object.put("student",homework.getStudent().getName());
            object.put("handle","是");
            array.add(object);
        }
        List<StuHomeworkDto> unhandled = service.getUnhandledHomeworks(email);
        for (StuHomeworkDto dto : unhandled)
        {
            JSONObject object = new JSONObject();
            object.put("semester",dto.getSemester());
            object.put("name",dto.getCourse());
            object.put("title",dto.getTitle());
            object.put("student",dto.getStudent());
            object.put("handle","否");
            array.add(object);
        }
        return array;
    }

}
