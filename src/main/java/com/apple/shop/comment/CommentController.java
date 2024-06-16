package com.apple.shop.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.PostMapping;

@Configuration
@RequiredArgsConstructor
public class CommentController {
    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    String postComment(){
        var data = new Comment;

        commentRepository.save();
        return "";
    }
}
