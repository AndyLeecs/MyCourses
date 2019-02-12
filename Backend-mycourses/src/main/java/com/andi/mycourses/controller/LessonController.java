package com.andi.mycourses.controller;

import com.andi.mycourses.entity.LessonPubVo;
import com.andi.mycourses.entity.LessonWhole;
import com.andi.mycourses.service.LessonService;
import com.andi.mycourses.util.DateUtil;
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
@RequestMapping("/api/v1/lesson")
public class LessonController {
    @Autowired
    LessonService service;

    @GetMapping("/current/teacher")
    public JSONArray cur(HttpServletRequest request)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> list = service.cur(email);

        JSONArray array = new JSONArray();
        for (LessonWhole lesson : list)
        {
            JSONObject object = new JSONObject();
            object.put("id",lesson.getId());
            object.put("course_id",lesson.getCourse().getId());
            object.put("name", lesson.getCourse().getName());
            array.add(object);
        }
        return array;
    }

    @PostMapping("/pub")
    public JSONObject pubLesson(HttpServletRequest request, @RequestBody JSONObject jsonObject)
    {
        JSONArray dateArray = jsonObject.getJSONArray("date");
        String startDate = (String)dateArray.get(0);
        String endDate = (String)dateArray.get(1);
        LessonPubVo lessonPubVo = new LessonPubVo(
                jsonObject.getLong("id"),
                DateUtil.getLocalDate(startDate),
                DateUtil.getLocalDate(endDate),
                jsonObject.getInt("count"),
                jsonObject.getInt("limit"),
                jsonObject.getBoolean("limitNum"));
        service.pub(lessonPubVo);
        JSONObject object = new JSONObject();
        return object;
    }
}
