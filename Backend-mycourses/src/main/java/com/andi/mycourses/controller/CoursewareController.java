package com.andi.mycourses.controller;

import com.andi.mycourses.entity.Courseware;
import com.andi.mycourses.entity.DBFile;
import com.andi.mycourses.service.CourseService;
import com.andi.mycourses.util.FileUtil;
import com.andi.mycourses.util.JsonUtil;
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

   @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        DBFile dbFile = courseService.getCourseware(fileId);
        return FileUtil.downloadFile(dbFile);
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
        boolean valid =  courseService.uploadCourseware(file, course_id);
        return JsonUtil.getValidObject(valid);
    }
}
