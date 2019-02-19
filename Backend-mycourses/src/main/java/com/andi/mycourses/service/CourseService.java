package com.andi.mycourses.service;

import com.andi.mycourses.entity.*;
import com.andi.mycourses.repo.*;
import com.andi.mycourses.util.FileUtil;
import com.andi.mycourses.vo.HomeworkPubVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author andi
 */
@Service
public class CourseService {
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    BaseUserRepo baseUserRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    CoursewareRepo coursewareRepo;
    @Autowired
    TopicRepo topicRepo;
    @Autowired
    CommentRepo commentRepo;

    public Topic getTopic(long topic_id)
    {
        return topicRepo.findById(topic_id).get();
    }

    public List<Comment> getAllComments(long topic_id)
    {
        Topic topic = topicRepo.findById(topic_id).get();
        return topic.getComments();
    }

    public Comment getComment(long course_id)
    {
        return commentRepo.findById(course_id).get();
    }

    public boolean pubComment(String email, Topic topic, Comment parent, String content, LocalDateTime time)
    {
        try {
            BaseUser user = baseUserRepo.findById(email).get();
            Comment comment = new Comment(parent,user, topic, content, time);
            commentRepo.save(comment);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Topic pubTopic(long course_id, String title)
    {
        Topic topic = null;
        try {
            Course course = courseRepo.findById(course_id).get();
            topic = new Topic(title,course);
            topic = topicRepo.save(topic);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return topic;
    }

    public List<Topic> getAllTopics(long course_id)
    {
        Course course = courseRepo.findById(course_id).get();
        return course.getTopics();
    }

    public Courseware getCourseware(String id)
    {
        return coursewareRepo.findById(id).get();
    }

    public List<Courseware> getAllCoursewares(long course_id)
    {
        Course course = courseRepo.findById(course_id).get();
        return course.getCoursewares();
    }

    public boolean uploadCourseware(MultipartFile file, long course_id)
    {
        DBFile dbFile = FileUtil.getDBFile(file);
        if (dbFile==null)return false;
        try {
            Course course = courseRepo.findById(course_id).get();
            Courseware courseware = new Courseware(dbFile,course );
            coursewareRepo.save(courseware);
//          coursewareRepo.upload(dbFile.getFileName(), dbFile.getFileType(), dbFile.getData(), course_id);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean apply(String teacher_email, String name)
    {
        try {
            //todo 写在dao层
            Course course = new Course();
            course.setName(name);
            Teacher teacher = teacherRepo.findById(teacher_email).get();
            System.out.println(teacher);
            course.setTeacher(teacher);
            courseRepo.save(course);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
