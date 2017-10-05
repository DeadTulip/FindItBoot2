package p.hh.fiboot2.controller;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.domain.DigitalItem;
import p.hh.fiboot2.dto.DigitalItemDto;
import p.hh.fiboot2.dto.ItemDto;
import p.hh.fiboot2.dto.PhysicalItemDto;
import p.hh.fiboot2.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDto createItem(HttpEntity<String> httpEntity) {
        String json = httpEntity.getBody();
        Map jsonMap = JsonParserFactory.getJsonParser().parseMap(json);
        if("Digital".equals(jsonMap.get("itemType"))) {
            DigitalItemDto diDto = new DigitalItemDto();
            modelMapper.map(jsonMap, diDto);
            return itemService.createDigitalItem(diDto);
        } else {
            PhysicalItemDto piDto = new PhysicalItemDto();
            modelMapper.map(jsonMap, piDto);
            return itemService.createPhysicalItem(piDto);
        }
    }

    @GetMapping("/{itemId}")
    public ItemDto readItem(@PathVariable Long itemId, HttpServletResponse response) {
        ItemDto itemDto = itemService.getItem(itemId);
        if (itemDto != null) {
            return itemDto;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PutMapping
    public ItemDto updateItem(@RequestBody ItemDto itemDto) {
        return itemService.updateItem(itemDto);
    }

    @DeleteMapping("/{itemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }
}
