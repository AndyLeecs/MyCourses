package com.andi.mycourses.service;

import com.andi.mycourses.entity.*;
import com.andi.mycourses.repo.*;
import com.andi.mycourses.util.FileUtil;
import com.andi.mycourses.vo.HomeworkPubVo;
import com.andi.mycourses.vo.LessonPubVo;
import com.andi.mycourses.vo.StuHomeworkDto;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author andi
 */
@Service
public class LessonService {
    @Autowired
    LessonRepo lessonRepo;
    @Autowired
    CourseRepo courseRepo;
    @Autowired
    HomeworkRepo homeworkRepo;
    @Autowired
    StuHomeworkRepo stuHomeworkRepo;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    ClassScoreRepo classScoreRepo;
    @Autowired
    EnrollRecordRepo recordRepo;
    @Autowired
    ScoreRepo scoreRepo;
    @Autowired
    MailService mailService;
    @Autowired
    TeacherRepo teacherRepo;

    public List<LessonWhole> getAttended(String email)
    {
        return lessonRepo.getAttended(email);
    }

    public List<LessonWhole> getDropout(String email)
    {
        return lessonRepo.getDropout(email);
    }

    public List<Score> getScores(String email)
    {
        return scoreRepo.getScores(email);
    }

    public List<EnrollRecord> getEnrollment(String email)
    {
        return recordRepo.getEnrollment(email);
    }

    public List<LessonWhole> getLessons(String email)
    {
        return lessonRepo.getAlllessons(email);
    }

    public List<StuHomework> getHomeworkStats(String email)
    {
        return stuHomeworkRepo.findByTeacher(email);
    }

    public List<StuHomeworkDto> getUnhandledHomeworks(String email)
    {
        List<StuHomeworkDto> dtos = new ArrayList<>();
        Teacher teacher = teacherRepo.findById(email).get();
        List<Course> courses = teacher.getCourses();
        for (Course c : courses)
        {
            String course = c.getName();
            for (LessonWhole l : c.getLessons())
            {
                String semester = l.getSemester();
                List<Student> students =
                        l.getRecords().stream().map(EnrollRecord::getStudent).collect(Collectors.toList());
                for (Homework h : l.getHomeworks()){
                    String title = h.getTitle();
                    List<Student> unhandled = students;
                    List<Student> handled =
                            h.getHomeworks().stream().map(StuHomework::getStudent).collect(Collectors.toList());
                    unhandled.removeAll(handled);
                    unhandled.forEach(student -> {
                        dtos.add(new StuHomeworkDto(semester, course, student.getName(), title));
                    });
                }
            }
        }
        return dtos;
    }

    public boolean sendMail(String title, String content, long lesson_id)
    {
        try {
            LessonWhole lesson = lessonRepo.findById(lesson_id).get();
            String name = lesson.getCourse().getName();
            List<Student> students = getStudentList(lesson_id);
            String[] emails = students.stream().map(BaseUser::getEmail).toArray(String[]::new);
            mailService.sendMail(emails, name + ":" + title, content);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public List<LessonWhole> getLessonsToEnroll(String email)
    {
        //获取全部未结束的课程
        return lessonRepo.getLessonsToEnroll(email);
    }

    public String enrollNewLesson(String email, long lesson_id, int whichClass)
    {
        String msg = "选课成功";
        try {
            Student student = studentRepo.findById(email).get();
            LessonWhole lessonWhole = lessonRepo.findById(lesson_id).get();

            //检查班额
            boolean hasLimit = lessonWhole.isHasLimit();
            System.out.println(hasLimit);
            int currentClass = 1;
            int classCount = lessonWhole.getCount();
            //如果有班额限制
            if (hasLimit) {
                int limit = lessonWhole.getLimitNum();
                //查看当前班级班额
                int hasEnrolled = recordRepo.countByLessonAndWhichClass(lessonWhole, whichClass);
                //如果超过班额，换一个班级，置whichClass为更换后的班级
                if (hasEnrolled >= limit) {
                    while (currentClass <= classCount) {
                        hasEnrolled = recordRepo.countByLessonAndWhichClass(lessonWhole, currentClass);
                        if (hasEnrolled < limit) {
                            whichClass = currentClass;
                            msg="系统自动为您调剂到"+currentClass+"班";
                            break;
                        }
                        currentClass++;
                    }
                    System.out.println(currentClass);
                }
            }

            //如果找不到合适的班级
            if (currentClass > classCount)msg="选课名额已满";
                //如果能找到合适的班级
            else {
                EnrollRecord record = new EnrollRecord(student, lessonWhole, whichClass);
                recordRepo.save(record);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            msg = "系统错误，请稍候再试";
        }
        return msg;
    }

    public List<LessonWhole> enrolled(String email)
    {
        List<EnrollRecord> enrollRecords = studentRepo.findById(email).get().getRecords();
        return enrollRecords.stream().map(EnrollRecord::getLesson).collect(Collectors.toList());
    }

    public boolean dropOut(String email, long lesson_id)
    {
        try {
            Student student = studentRepo.findById(email).get();
            LessonWhole lessonWhole = lessonRepo.findById(lesson_id).get();
            recordRepo.deleteDistinctByStudentAndLesson(student, lessonWhole);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public DBFile getClassScores(String title)
    {
        DBFile file = classScoreRepo.findDistinctFirstByFileNameStartsWith(title);
        return file;
    }

    public List<Score> getSelfScores(String email, long lesson_id)
    {
        System.out.println(lesson_id);
        Student student = studentRepo.findById(email).get();
        System.out.println(student);
        LessonWhole lessonWhole = lessonRepo.findById(lesson_id).get();
        System.out.println(lessonWhole);
        EnrollRecord record = recordRepo.findDistinctFirstByStudentAndLesson(student, lessonWhole);
        System.out.println(record);
        return record.getScores();
    }

    public boolean uploadScore(MultipartFile file, long lesson_id, boolean set_public)
    {
        DBFile dbFile = FileUtil.getDBFile(file);
        if (dbFile==null)
            return false;
        try {
            //存文件
            ClassScore classScore = new ClassScore(dbFile);
            classScoreRepo.save(classScore);
            String title = FileUtil.getPureName(dbFile);
            LessonWhole lessonWhole = lessonRepo.findById(lesson_id).get();
//            //1.获取文件输入流
           InputStream inputStream = new ByteArrayInputStream(dbFile.getData());
            //2.获取excel工作簿对象
//            File excelFile = File.createTempFile("score","xls");
//            OutputStream os = new FileOutputStream(excelFile);
//            BufferedOutputStream bos = new BufferedOutputStream(os);
//            bos.write(dbFile.getData());
//            Workbook workbook = WorkbookFactory.create(excelFile);
            Workbook workbook = new XSSFWorkbook(inputStream);
            //3.得到excel工作表对象
            Sheet sheet = workbook.getSheetAt(0);
            //4.循环读取表格数据（除表头）
            for (Row row: sheet){
                if (row.getRowNum() == 0){
                    continue;
                }
                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                Long sid = Long.valueOf(row.getCell(0).getStringCellValue());
                BigDecimal scoreNum = BigDecimal.valueOf(row.getCell(1).getNumericCellValue());
                Student student = studentRepo.findDistinctBySid(sid);
                EnrollRecord enrollRecord = recordRepo.findDistinctFirstByStudentAndLesson(student, lessonWhole);
                Score score = new Score(title, scoreNum, enrollRecord, set_public);
                scoreRepo.save(score);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;

    }
    public List<Student> getStudentList(long lesson_id) {
        return studentRepo.getStudentList(lesson_id);
    }

    public DBFile downloadAllHomeworks(String rootPath, long id) {
        Homework homework = homeworkRepo.findById(id).get();
        List<StuHomework> stuHomeworks = stuHomeworkRepo.findByHomework(homework);
        stuHomeworks.forEach(StuHomework::rename);
        return FileUtil.mergeToZip(rootPath, stuHomeworks);
    }
    public StuHomework downloadSelfHomework(String email, long homework_id)
    {
        return stuHomeworkRepo.findSelf(email, homework_id);
    }

    public boolean uploadHomework(MultipartFile file, long id, String email)
    {
        DBFile dbFile = FileUtil.getDBFile(file);
        if (dbFile==null)return false;
        //检查之前是否提交过
        DBFile formerHomework = downloadSelfHomework(email, id);
        String stuHomeworkId = "";
        try {
            if (formerHomework != null){
                stuHomeworkId = formerHomework.getId();
            }
            Homework homework = homeworkRepo.findById(id).get();
            Student student = studentRepo.findById(email).get();
            StuHomework stuHomework = new StuHomework(dbFile, homework, student);
            if (formerHomework != null){
                stuHomework.setId(stuHomeworkId);
            }
            stuHomeworkRepo.save(stuHomework);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean hasSubmitted(Homework homework, String email)
    {
        return (stuHomeworkRepo.hasSubmitted(email, homework.getId()).size()>0);
    }

    public boolean pubHomework(HomeworkPubVo vo)
    {
        try {
            LessonWhole lessonWhole = lessonRepo.findById(vo.getLesson_id()).get();
            Homework homework = new Homework(vo, lessonWhole);
            homeworkRepo.save(homework);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Homework getHomework(long id){ return homeworkRepo.findById(id).get();}

    public List<Homework> getAllHomeworkPubs(long lesson_id)
    {
        LessonWhole lesson = lessonRepo.findById(lesson_id).get();
        return lesson.getHomeworks();
    }


    public List<LessonWhole> cur(String email)
    {
        List<LessonWhole> lessonWholes = new ArrayList<>();
        try {
            lessonWholes = lessonRepo.getCurrentlessons(email);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return lessonWholes;
    }

    public boolean pub(LessonPubVo vo) {
        try {
            long course_id = vo.getCourse_id();
            Course course = courseRepo.findById(course_id).get();
            LessonWhole lesson = new LessonWhole(course,vo);
            lessonRepo.save(lesson);
        }catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
