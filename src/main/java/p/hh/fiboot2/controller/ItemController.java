package p.hh.fiboot2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.domain.DigitalItem;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.PhysicalItem;
import p.hh.fiboot2.domain.User;
import p.hh.fiboot2.dto.DigitalItemDto;
import p.hh.fiboot2.dto.ItemDto;
import p.hh.fiboot2.service.ItemService;
import p.hh.fiboot2.service.UserService;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @PostMapping
    public ItemDto createItem(@RequestBody ItemDto itemDto) {
        return itemService.createItem(itemDto);
    }

    @GetMapping("/{itemId}")
    public ItemDto readItem(@PathVariable Long itemId) {
        return itemService.getItem(itemId);
    }

    @PutMapping
    public ItemDto updateItem(@RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemDto);
    }

    @DeleteMapping("/{itemId}")
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }
}
