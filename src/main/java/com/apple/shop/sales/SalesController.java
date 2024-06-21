package com.apple.shop.sales;

import com.apple.shop.Member.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SalesController {

    private final SalesRepository salesRepository;

    @PostMapping("/order")
    String postOrder(@RequestParam String title,
                     @RequestParam Integer price,
                     @RequestParam Integer count,
                     Authentication auth) {
        CustomUser user = (CustomUser) auth.getPrincipal();
        Sales sales = new Sales();
        sales.setPrice(price);
        sales.setCount(count);
        sales.setItemName(title);
        sales.setMemberId(user.memberId);
        salesRepository.save(sales);

        return "redirect:/list/page/1";
    }

}
