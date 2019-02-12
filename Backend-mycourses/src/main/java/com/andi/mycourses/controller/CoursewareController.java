package com.andi.mycourses.controller;

import com.andi.mycourses.entity.Courseware;
import com.andi.mycourses.entity.DBFile;
import com.andi.mycourses.service.CourseService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLEncoder;
import java.util.List;

/**
 * @author andi
 */
@CrossOrigin
@RestController
@RequestMapping("/api/v1/courseware")
public class CoursewareController {
    @Autowired
    CourseService courseService;
//    @PostMapping("/upload")
//    public JSONObject upload(@RequestBody JSONObject jsonObject)
//    {
//        long course_id = jsonObject.getLong("course_id");
//        MultipartFile file = (MultipartFile) jsonObject.get("file");
//        boolean valid =  courseService.uploadCourseware(file, course_id);
//
//        JSONObject object = new JSONObject();
//        object.put("valid",valid);
//        return object;
//    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database

        DBFile dbFile = courseService.getCourseware(fileId);
        String fileName = dbFile.getFileName();
        try {
            //todo chrome可以，火狐不行，怎么兼容多个浏览器
            //todo 怎么直接返回那个url，考虑ajax的原理，get和post的区别
            fileName = URLEncoder.encode(dbFile.getFileName(), "UTF-8");
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(fileName);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }


    @GetMapping("/all")
    public JSONArray all(@RequestParam("course_id") long course_id )
    {
        //todo 改成在dao中只获取需要的域
        List<Courseware> coursewares = courseService.getAllCoursewares(course_id);
        JSONArray array = new JSONArray();
        for (Courseware courseware : coursewares)
        {
            JSONObject object = new JSONObject();
            object.put("id",courseware.getId());
            object.put("name",courseware.getFileName());
            array.add(object);
        }
        return array;
    }

    @PostMapping("/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file, @RequestParam("course_id") long course_id)
    {
        System.out.println("uploading");
//        long course_id = reqobject.getLong("course_id");
        boolean valid =  courseService.uploadCourseware(file, course_id);

        JSONObject object = new JSONObject();
        object.put("valid",valid);
        return object;
    }
}
