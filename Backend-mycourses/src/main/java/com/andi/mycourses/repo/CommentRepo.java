package com.andi.mycourses.repo;

import com.andi.mycourses.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author andi
 */
public interface CommentRepo extends JpaRepository<Comment,Long> {
}
