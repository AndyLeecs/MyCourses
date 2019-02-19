package com.andi.mycourses.controller;

import com.andi.mycourses.entity.DBFile;
import com.andi.mycourses.entity.Score;
import com.andi.mycourses.entity.Student;
import com.andi.mycourses.service.LessonService;
import com.andi.mycourses.util.FileUtil;
import com.andi.mycourses.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1/stuList")
public class StuListController {
    @Autowired
    LessonService service;

    @PostMapping("/mail")
    public JSONObject sendMail(@RequestBody JSONObject object)
    {
        System.out.println("in send mail");
        String title = object.getString("title");
        String content = object.getString("content");
        long lesson_id = object.getLong("lesson_id");
        return JsonUtil.getValidObject(service.sendMail(title, content, lesson_id));
    }

    @GetMapping("/all/{lesson_id}")
    public JSONArray getStuList(@PathVariable long lesson_id)
    {
        System.out.println("in get stulist");
        List<Student> students = service.getStudentList(lesson_id);
        JSONArray array = new JSONArray();
        for (Student student : students)
        {
            JSONObject object = new JSONObject();
            object.put("id",student.getSid());
            object.put("name",student.getName());
            array.add(object);
        }
        return array;
    }

    @PostMapping("/score/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file,
                             @RequestParam("lesson_id") long lesson_id,
                             @RequestParam("set_public") boolean set_public)
    {
        System.out.println("uploading");
        boolean valid =  service.uploadScore(file, lesson_id, set_public);
        return JsonUtil.getValidObject(valid);
    }

    @GetMapping("/score/all/{lesson_id}")
    public JSONArray getSelfScores(HttpServletRequest request, @PathVariable long lesson_id)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<Score> records = service.getSelfScores(email, lesson_id);
        System.out.println(records);
        JSONArray array = new JSONArray();
        for (Score record : records) {
            JSONObject object = new JSONObject();
            object.put("id", record.getId());
            object.put("name", record.getTitle());
            object.put("value" ,record.getScore());
            object.put("has_extra",record.isShowPublic());
            array.add(object);
        }
        return array;
    }

//    @GetMapping("/score/self/{score_id}")
//    public JSONObject getSelfScore(HttpServletRequest request, @PathVariable String score_id)
//    {
//        EnrollRecord record = service.getSelfScore(score_id);
//
//    }

    @GetMapping("/score/download/{title}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String title) {
        DBFile dbFile = service.getClassScores(title);
        return FileUtil.downloadFile(dbFile);
    }

    @PostMapping("/dropOut/{lesson_id}")
    public JSONObject dropOut(HttpServletRequest request, @PathVariable long lesson_id)
    {
        String email = SessionUtil.getSessionEmail(request);
        boolean valid = service.dropOut(email, lesson_id);
        return JsonUtil.getValidObject(valid);
    }
}
