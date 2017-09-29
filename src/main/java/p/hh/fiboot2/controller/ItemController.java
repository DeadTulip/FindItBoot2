package p.hh.fiboot2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.dto.ItemDto;
import p.hh.fiboot2.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ItemDto createItem() {
        return null;
    }

    @GetMapping("/{itemId}")
    public ItemDto readItem(@PathVariable Long itemId) {
        Item item = itemService.getItem(itemId);
        return modelMapper.map(item, ItemDto.class);
    }

    @PostMapping("/udpate")
    public ItemDto updateItem() {
        return null;
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }
}
