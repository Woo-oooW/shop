package com.apple.shop.comment;

import com.apple.shop.Member.CustomUser;
import com.apple.shop.Member.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentRepository commentRepository;

    @PostMapping("/comment")
    String postComment(@RequestParam String content,
                       @RequestParam Long parent,
                       Authentication auth){

        CustomUser user = (CustomUser) auth.getPrincipal();

        var data = new Comment();
        data.setContent(content);
        data.setUsername(user.getUsername());
        data.setParentId(parent);
        commentRepository.save(data);
        return "redirect:/list";
    }
    
    //후에 페이지네이션
}
