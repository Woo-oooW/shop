package com.apple.shop.Member;

import com.apple.shop.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public Object memberCount(){
        List<Member> result = memberRepository.findAll();
        return result;
    }
}
