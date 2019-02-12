package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Courseware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

/**
 * @author andi
 */
public interface CoursewareRepo extends JpaRepository<Courseware, String> {
    @Modifying
    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true,
            value = "insert into Courseware (file_name, file_type, data, course_id)" +
                    "values (:fileName, :fileType, :data, :course_id)")
    void upload(@Param("fileName")String fileName,
                @Param("fileType")String fileType,
                @Param("data")byte[] data,
                @Param("course_id")long course_id);

}
