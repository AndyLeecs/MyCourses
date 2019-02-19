package com.andi.mycourses.repo;

import com.andi.mycourses.entity.ClassScore;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author andi
 */
public interface ClassScoreRepo extends JpaRepository<ClassScore, Long> {
    ClassScore findDistinctFirstByFileNameStartsWith(String title);
}
