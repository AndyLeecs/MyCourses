package com.andi.mycourses.controller;

import com.andi.mycourses.entity.Comment;
import com.andi.mycourses.entity.Topic;
import com.andi.mycourses.service.CourseService;
import com.andi.mycourses.util.DateUtil;
import com.andi.mycourses.util.JsonUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author andi
 */
@RestController
@RequestMapping("/api/v1/forum")
public class ForumController {
    @Autowired
    CourseService service;

    @GetMapping("/detail/{topic_id}")
    public JSONArray getDetail(@PathVariable long topic_id)
    {
        List<Comment> comments = service.getAllComments(topic_id);
        JSONArray array = new JSONArray();
        //构造树形结构
        for (Comment comment : comments)
        {
            if (comment.getReplyTo()==null) {
                JSONObject object = JsonUtil.getJsonForComment(comment);
                array.add(object);
            }
        }
        System.out.println("comments "+array);
        return array;
    }

    @GetMapping("/all/{course_id}")
    public JSONArray getAllTopics(@PathVariable long course_id)
    {
        List<Topic> topics = service.getAllTopics(course_id);
        JSONArray array = new JSONArray();
        for (Topic t: topics)
        {
            JSONObject obj = new JSONObject();
            obj.put("id",t.getId());
            obj.put("name",t.getTitle());
            array.add(obj);
        }
        return array;
    }

    @PostMapping("/reply")
    public JSONArray reply(HttpServletRequest request, @RequestBody JSONObject object)
    {
        long topic_id = object.getLong("id");
        Topic topic = service.getTopic(topic_id);
        long comment_id = object.getLong("parent_id");
        Comment parent = service.getComment(comment_id);
        pubCommentGivenTopicAndParent(request, object, parent, topic);
        return getDetail(topic_id);
    }

    @PostMapping("/pubComment")
    public JSONArray pubComment(HttpServletRequest request, @RequestBody JSONObject object)
    {
        long topic_id = object.getLong("id");
        Topic topic = service.getTopic(topic_id);
        pubCommentGivenTopicAndParent(request, object, null, topic);
        return getDetail(topic_id);
    }

    private void pubCommentGivenTopicAndParent(HttpServletRequest request, @RequestBody JSONObject object, Comment parent
                                               ,Topic topic) {
        String content = object.getString("content");
        LocalDateTime time = DateUtil.getLocalDateTime(object.getString("time"));
        String email = SessionUtil.getSessionEmail(request);
        service.pubComment(email, topic, parent, content, time);
    }

    @PostMapping("/pubTopic")
    public JSONArray pubTopic(HttpServletRequest request, @RequestBody JSONObject object)
    {
        //发布主题
        long course_id = object.getLong("course_id");
        String title = object.getString("title");
        Topic topic = service.pubTopic(course_id, title);

        //发布第一条内容
        pubCommentGivenTopicAndParent(request, object,null, topic);

        //返回全部主题
        return this.getAllTopics(course_id);

    }
}
