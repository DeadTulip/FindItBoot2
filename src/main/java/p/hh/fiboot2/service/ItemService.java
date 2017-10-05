package p.hh.fiboot2.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot2.dao.ItemDao;
import p.hh.fiboot2.dao.UserDao;
import p.hh.fiboot2.domain.DigitalItem;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.PhysicalItem;
import p.hh.fiboot2.domain.User;
import p.hh.fiboot2.dto.DigitalItemDto;
import p.hh.fiboot2.dto.ItemDto;
import p.hh.fiboot2.dto.MappingUtil;
import p.hh.fiboot2.dto.PhysicalItemDto;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    public ItemDto getItem(Long itemId) {
        Item item = itemDao.findOne(itemId);
        if (item == null) {
            return null;
        } else if (item instanceof DigitalItem) {
            return modelMapper.map((DigitalItem)item, DigitalItemDto.class);
        } else {
            return modelMapper.map((PhysicalItem)item, PhysicalItemDto.class);
        }
    }

    public DigitalItemDto createDigitalItem(DigitalItemDto itemDto) {
        User owner = userDao.findOne(itemDto.getOwner().getUserId());
        DigitalItem di = modelMapper.map(itemDto, DigitalItem.class);
        di.setOwner(owner);
        DigitalItem createdDi = itemDao.save(di);
        return modelMapper.map(createdDi, DigitalItemDto.class);
    }

    public PhysicalItemDto createPhysicalItem(PhysicalItemDto itemDto) {
        User owner = userDao.findOne(itemDto.getOwner().getUserId());
        PhysicalItem pi = modelMapper.map(itemDto, PhysicalItem.class);
        pi.setOwner(owner);
        PhysicalItem createdPi = itemDao.save(pi);
        return modelMapper.map(createdPi, PhysicalItemDto.class);
    }

    public ItemDto updateItem(ItemDto itemDto) {
        Item item = itemDao.findOne(itemDto.getItemId());
        modelMapper.map(itemDto, item);
        Item savedItem = itemDao.save(item);

        if (item instanceof DigitalItem) {
            return modelMapper.map((DigitalItem)item, DigitalItemDto.class);
        } else {
            return modelMapper.map((PhysicalItem)item, DigitalItemDto.class);
        }
    }

    public ItemDto[] getAllItemsCreatedByUser(Long userId) {
        User user = userDao.findOne(userId);
        List<Item> items = itemDao.findAllByOwner(user);
        return MappingUtil.mapItemList(modelMapper, items);
    }

    public void deleteAllItemsCreatedByUser(Long userId) {
        User user = userDao.findOne(userId);
        List<Item> items = itemDao.findAllByOwner(user);
        itemDao.delete(items);
    }

    public void deleteItem(Long itemId) {
        itemDao.delete(itemId);
    }

}
