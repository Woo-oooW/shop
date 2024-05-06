package com.apple.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @GetMapping("/main")
    String main()
    {
         return "Member/login.html";
    }

    @GetMapping("/join")
    String join()
    {
        return "Member/join.html";
    }

    @PostMapping("/new")
    String newJoin(String username, String password, String displaynm){
        memberService.joinMember(username, password, displaynm);
        return "redirect:/main";
    }

    @GetMapping("/my-page")
    String myPage(Authentication auth, Model model)
    {

        return "Member/mypage.html";
    }



}
