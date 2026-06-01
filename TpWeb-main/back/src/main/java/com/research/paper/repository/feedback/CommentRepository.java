package com.research.paper.repository.feedback;

import com.research.paper.entity.feedback.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,String> {
}
