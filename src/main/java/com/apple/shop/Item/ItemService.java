package com.apple.shop.Item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Object listItem(){
        List<Item> result = itemRepository.findAll();
        return result;
    }

    public Object detailItem(Long id){
        Optional<Item> result = itemRepository.findById(id);
        return result.get();
    }

    public void saveItem(String title, Integer price){
        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
    }

    public Object preEditItem(Long id){
        Optional<Item> result = itemRepository.findById(id);
        return result.get();
    }

    public void editItem(Long id, String title, Integer price){
        var result = itemRepository.findById(id).orElseThrow();
        result.setTitle(title);
        result.setPrice(price);
        itemRepository.save(result);
    }

    public Object delItem(Long id){
        var result = itemRepository.findById(id).orElseThrow();
        itemRepository.delete(result);
        return null;
    }
}
