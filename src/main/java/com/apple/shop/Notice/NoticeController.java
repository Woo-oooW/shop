package com.apple.shop.Notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice")
    String list(Model model){
        List<Notice> result = noticeRepository.findAll();

        model.addAttribute("notice", result);
        return "notice";
    }
}
