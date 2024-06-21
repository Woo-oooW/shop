package com.apple.shop.Item;

import com.apple.shop.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.lang.*;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;
    private final CommentRepository commentRepository;

    @GetMapping("/list")
    String list(Model model){
        return "redirect:/list/page/1";
    }

    @GetMapping("/list/page/{g_num}")
    String getListPage(Model model,@PathVariable Integer g_num) {
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(g_num-1,3));
        model.addAttribute("items", result);
        model.addAttribute("pageNumber", g_num);
        return "item/list.html";
    }

    @PostMapping("/search")
    String postSearch(@RequestParam String searchText, Model model) {
        var result = itemService.listSearchItem(searchText);
        model.addAttribute("items", result);
        return "item/slist.html";
    }

    @GetMapping("/write")
    String write(){
        return "item/write.html";
    }

    @PostMapping("/add")
    String addPost(String title, Integer price){
        itemService.saveItem(title, price);
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(1,3));
        return "redirect:/list/page/" + result.getTotalPages();
    }

    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model){
        model.addAttribute("data",itemService.detailItem(id));
        model.addAttribute("comments",commentRepository.findAllByParentId(id));
        return "item/detail.html";
    }

    @GetMapping("/edit/{id}")
    String preEdit(@PathVariable Long id, Model model){
        model.addAttribute("data",itemService.preEditItem(id));
        return "item/edit.html";
    }

    @PostMapping("/edit/save")
    String edit(Long id, String title, Integer price) throws Exception {
        if(title.length() > 100 || price < 0)
        {
            throw new Exception();
        }
        itemService.editItem(id,title,price);
        return "redirect:/list";
    }

    @GetMapping("/del/{id}")
    String delPost(@PathVariable Long id, Model model){
        model.addAttribute("data",itemService.delItem(id));
        return "redirect:/list";
    }

    @PostMapping("/PostDel")
    String test(@RequestBody Map<String, Object> body, Model model){
        int iid = (int) body.get("id");
        Long id = (long) iid;
        model.addAttribute("data",itemService.delItem(id));
        return "redirect:/list";
    }


}