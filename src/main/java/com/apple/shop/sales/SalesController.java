package com.apple.shop.sales;

import com.apple.shop.Member.CustomUser;
import com.apple.shop.Member.Member;
import com.apple.shop.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/order")
    String postOrder(@RequestParam String title,
                     @RequestParam Integer price,
                     @RequestParam Integer count,
                     Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Sales sales = new Sales();
        var member = new Member();
        sales.setPrice(price);
        sales.setCount(count);
        sales.setItemName(title);
        member.setId(user.memberId);
        sales.setMember(member);
        System.out.println(sales);
        System.out.println(member);
        salesRepository.save(sales);
        return "redirect:/list";
    }

    @GetMapping("/order/all")
    String getOrderAll()
    {//다듬어두자
    List<Sales> result = salesRepository.customFindAll();
//        var salesDto = new SalesDto();
//        salesDto.itemName = result.get(0).getItemName();
//        salesDto.price = result.get(0).getPrice();
//        salesDto.username = result.get(0).getMember().getUsername();
        var result2 = memberRepository.findById(1L);
        System.out.println(result2.get());
        return "redirect:/list";
    }

    class SalesDto{
        public String itemName;
        public Integer price;
        public String username;
    }

}
