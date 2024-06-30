package com.apple.shop.Member;

import com.apple.shop.Config.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

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
        return "Member/mypage.html";
    }

    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser(){
        var a = memberRepository.findById(1L);
        var result = a.get();
        var data = new MemberDto(result.getUsername(),result.getDisplaynm());
        return data;
    }

    @GetMapping("/mypage/jwt")
    @ResponseBody
    String mypageJWT(){

        return "item/list.html";
    }



    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data
    ,HttpServletResponse response) {
        System.out.println(data.get("username"));
        System.out.println(data.get("password"));
        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );

        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());
        System.out.println(jwt);

        var cookie = new Cookie("jwt",jwt);
        cookie.setMaxAge(10);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);

        return jwt;
    }
}

class MemberDto{
    public String username;
    public String displayName;
    public Long memberId;
    MemberDto(String a, String b){
        this.username = a;
        this.displayName = b;

    }
    MemberDto(String a, String b, Long id){
        this.username = a;
        this.displayName = b;
        this.memberId = id;
    }
}