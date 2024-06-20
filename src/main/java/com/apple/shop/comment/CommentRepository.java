package com.apple.shop.comment;

import com.apple.shop.Member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByParentId(Long parentId);
}
