package p.hh.fiboot2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.dto.ItemDto;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public ItemDto createItem() {
        return null;
    }

    @GetMapping("/{itemId}")
    public ItemDto readItem(@PathVariable Long itemId) {
        return null;
    }

    @PostMapping("/udpate")
    public ItemDto updateItem() {
        return null;
    }

    @DeleteMapping("/{itemId}")
    public ItemDto deleteItem() {
        return null;
    }
}
