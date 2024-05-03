package com.apple.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/main")
    String main(){
        return "login.html ";
    }

    @PostMapping("/login")
    String login(@RequestBody Map<String, Object> body, Model model){
        String user_id = body.get("username"); //수정해야함
        Long id = (long) iid;
        model.addAttribute("data",memberService.login(id));
        return "redirect:/list";
    }
}

//Member 테이블
//username, password, displayName
// password컬럼은 test2 참고해서 해싱해서 비번저장.
