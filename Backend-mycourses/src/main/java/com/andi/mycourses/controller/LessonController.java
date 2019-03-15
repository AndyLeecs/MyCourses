package com.andi.mycourses.controller;

import com.andi.mycourses.entity.LessonWhole;
import com.andi.mycourses.service.LessonService;
import com.andi.mycourses.util.DateUtil;
import com.andi.mycourses.util.JsonUtil;
import com.andi.mycourses.vo.LessonPubVo;
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

    @GetMapping("/enroll/all")
    public JSONArray getLessonsToEnroll(HttpServletRequest request)
    {
        System.out.println("in get lessons to enroll");
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> list = service.getLessonsToEnroll(email);
        JSONArray array = new JSONArray();
        for (LessonWhole lesson : list)
        {
            for (int i = 1 ; i <= lesson.getCount() ;i++) {
                JSONObject object = getJsonObject(lesson);
                if (lesson.getCount() > 1)
                    object.put("value",i);
                array.add(object);
            }
        }
        return array;
    }

    private JSONObject getJsonObject(LessonWhole lesson) {
        JSONObject object = new JSONObject();
        object.put("id", lesson.getId());
        object.put("course_id", lesson.getCourse().getId());
        object.put("name", lesson.getCourse().getName());
        return object;
    }

    @PostMapping("/enroll/{lesson_id}/{whichClass}")
    public JSONObject enrollNewLesson(HttpServletRequest request,
                                      @PathVariable long lesson_id,
                                      @PathVariable int whichClass)
    {
        String email = SessionUtil.getSessionEmail(request);
        JSONObject object = new JSONObject();
        object.put("msg",service.enrollNewLesson(email, lesson_id, whichClass));
        return object;
    }

    @GetMapping("/current/student")
    public JSONArray enrolled(HttpServletRequest request)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> list = service.enrolled(email);
        return getJsonArray(list);
    }

    @GetMapping("/past/teacher")
    public JSONArray past(HttpServletRequest request) {
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> list = service.past(email);
        return getJsonArray(list);
    }

    @GetMapping("/current/teacher")
    public JSONArray cur(HttpServletRequest request)
    {
        String email = SessionUtil.getSessionEmail(request);
        List<LessonWhole> list = service.cur(email);

        JSONArray array = getJsonArray(list);
        return array;
    }

    private JSONArray getJsonArray(List<LessonWhole> list) {
        JSONArray array = new JSONArray();
        for (LessonWhole lesson : list)
        {
            JSONObject object = getJsonObject(lesson);
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
        JSONObject object = jsonObject.getJSONObject("semester");
        String[] semesterDetail = {object.getString("start"),object.getString("end"),object.getString("rank")};
        LessonPubVo lessonPubVo = new LessonPubVo(
                jsonObject.getLong("id"),
                DateUtil.getLocalDate(startDate),
                DateUtil.getLocalDate(endDate),
                jsonObject.getInt("count"),
                jsonObject.getInt("limit"),
                jsonObject.getBoolean("limitNum"),
                semesterDetail);
        return JsonUtil.getValidObject(service.pub(lessonPubVo));
    }
}
