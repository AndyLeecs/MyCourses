package com.andi.mycourses.controller;

import com.andi.mycourses.entity.Courseware;
import com.andi.mycourses.entity.DBFile;
import com.andi.mycourses.entity.Homework;
import com.andi.mycourses.service.CourseService;
import com.andi.mycourses.service.LessonService;
import com.andi.mycourses.util.DateUtil;
import com.andi.mycourses.util.FileUtil;
import com.andi.mycourses.util.JsonUtil;
import com.andi.mycourses.util.RoleUtil;
import com.andi.mycourses.vo.HomeworkPubVo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.Messaging.SyncScopeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author andi
 * todo controller 层的各种可能的空指针
 */
@RestController
@RequestMapping("/api/v1/homework")
public class HomeworkController {
    @Autowired
    LessonService service;

    @PostMapping("/upload")
    public JSONObject upload(
            HttpServletRequest request, @RequestParam("file") MultipartFile file,
                             @RequestParam("id")long id)
    {
        String email = SessionUtil.getSessionEmail(request);
        System.out.println(email);
        return JsonUtil.getValidObject(service.uploadHomework(file, id, email));
    }

    @GetMapping("/self/{id}")
    public ResponseEntity<Resource> downloadSelf(HttpServletRequest request, @PathVariable long id)
    {
        DBFile dbFile = service.downloadSelfHomework(SessionUtil.getSessionEmail(request),id);
        return FileUtil.downloadFile(dbFile);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadAll(HttpServletRequest request, @PathVariable long id)
    {
        System.out.println("start to downloadAll in controller");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        return FileUtil.downloadFile(service.downloadAllHomeworks(rootPath,id));
    }

    @PostMapping("/pub")
    public JSONObject pub(@RequestBody HomeworkPubVo vo)
    {
        JSONObject object = new JSONObject();
        object.put("valid", service.pubHomework(vo));
        return object;
    }

    @GetMapping("/all/{lesson_id}")
    public JSONArray all(@PathVariable long lesson_id )
    {
        //todo 改成在dao中只获取需要的域
        List<Homework> homeworks = service.getAllHomeworkPubs(lesson_id);
        JSONArray array = new JSONArray();
        for (Homework homework : homeworks)
        {
            JSONObject object = new JSONObject();
            object.put("id",homework.getId());
            object.put("name",homework.getTitle());
            array.add(object);
        }
        System.out.println(array.toString());
        return array;
    }

    @GetMapping("/detail/{id}")
    public JSONObject detail(HttpServletRequest request, @PathVariable long id) {
        Homework homework = service.getHomework(id);
        JSONObject object = new JSONObject();
        object.put("id",homework.getId());
        object.put("lesson_id", homework.getLesson().getId());
        object.put("title",homework.getTitle());
        object.put("content",homework.getContent());
        object.put("ddl", DateUtil.getTimeString(homework.getDdl()));
        object.put("hasSizeLimit",homework.isHasSizeLimit());
        object.put("sizeLimit",homework.getSizeLimit());
        object.put("typesLimit",homework.getTypesLimit());

        String email = SessionUtil.getSessionEmail(request);
        if (RoleUtil.isStu(email))
        {
            object.put("submitted",service.hasSubmitted(homework, email));
        }

        System.out.println("homework "+object.toString());
        return object;
    }
}
