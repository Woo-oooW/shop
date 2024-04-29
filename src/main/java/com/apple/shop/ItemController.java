package com.apple.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.print.attribute.Attribute;
import java.util.*;


@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final ItemService itemService;

    @GetMapping("/list")
    String list(Model model){
        model.addAttribute("items", itemService.listItem());
        return "list.html";
    }

    @GetMapping("/write")
    String write(){
        return "write.html";
    }

    @PostMapping("/add")
    String addPost(String title, Integer price){
        itemService.saveItem(title, price);
        return "redirect:/list";
    }

    @GetMapping("/edit/{id}")
    String preEdit(@PathVariable Long id, Model model){
        model.addAttribute("data",itemService.preEditItem(id));
        return "edit.html";
    }

    @PostMapping("/edit/save")
    String edit(Long id, String title, Integer price){
        itemService.editItem(id,title,price);
        return "redirect:/edit/" +id;
    }


    @GetMapping("/detail/{id}")
    String detail(@PathVariable Long id, Model model){
        model.addAttribute("data",itemService.detailItem(id));
        return "detail.html";
    }

}
