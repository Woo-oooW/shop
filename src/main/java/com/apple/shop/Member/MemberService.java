package com.apple.shop.Member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Object memberInfoName(String username){
        var result = memberRepository.findByUsername(username);
        return result;
    }
    public Object memberInfoId(Long memberId){
        var result = memberRepository.findById(memberId);
        return result;
    }

    public void joinMember(String username, String password, String displayname){
        Member member = new Member();
        String hpwd = passwordEncoder.encode(password);
        member.setUsername(username);
        member.setPassword(hpwd);
        member.setDisplaynm(displayname);
        memberRepository.save(member);
    }
}