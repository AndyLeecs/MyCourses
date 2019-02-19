package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author andi
 */
public interface ScoreRepo extends JpaRepository<Score, Long> {
    @Transactional(rollbackOn = Exception.class)
    @Query(nativeQuery = true, value =
            "select * from score where record_id in (" +
                    "select id from enroll_record where student_email = :email)")
    List<Score> getScores(@Param("email") String email);
}
