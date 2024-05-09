package com.apple.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
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
    String myPage(Authentication auth)
    {
        CustomUser result = (CustomUser) auth.getPrincipal();
        System.out.println(result.displayName);
        return "Member/mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser(){
        var a = memberRepository.findById(1L);
        var result = a.get();
        System.out.println(result);
        var data = new MemberDto(result.getUsername(),result.getDisplaynm());
        return data;
    }
}

class MemberDto{
    public String username;
    public String displayName;
    MemberDto(String a, String b){
        this.username = a;
        this.displayName = b;

    }
    MemberDto(String a, String b, Long id){
        this.username = a;
        this.displayName = b;

    }
}