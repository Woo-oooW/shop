package com.apple.shop.Member;

import com.apple.shop.Config.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
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


    @GetMapping("/login")
    public String main(HttpServletRequest request, HttpServletResponse response) {
        // 기존에 존재하는 JWT 쿠키 삭제
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        // 로그인 페이지로 이동
        return "Member/login";
    }

    @GetMapping("/join")
    String join()
    {
        return "Member/join";
    }

    @PostMapping("/new")
    String newJoin(String username, String password, String displaynm){
        memberService.joinMember(username, password, displaynm);
        return "redirect:/login";
    }

    @GetMapping("/my-page")
    String myPage(Authentication auth)
    {
        CustomUser result = (CustomUser) auth.getPrincipal();
        return "Member/mypage";
    }

    @GetMapping("/user")
    @ResponseBody
    public MemberDto getUser(Authentication auth){

        var userinfo = (CustomUser) auth.getPrincipal();
        var data = new MemberDto(userinfo.getUsername(), userinfo.getDisplayName(), userinfo.getMemberId());
        return data;
    }

    @PostMapping("/login/jwt")
    @ResponseBody
    public String loginJWT(@RequestBody Map<String, String> data
    ,HttpServletResponse response) {

        var authToken = new UsernamePasswordAuthenticationToken(
                data.get("username"), data.get("password")
        );

        var auth = authenticationManagerBuilder.getObject().authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);

        var jwt = JwtUtil.createToken(SecurityContextHolder.getContext().getAuthentication());

        var cookie = new Cookie("jwt",jwt);
        cookie.setMaxAge(1000);
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