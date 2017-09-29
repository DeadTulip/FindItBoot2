package p.hh.fiboot2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot2.dao.ItemDao;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.User;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemDao itemDao;

    public Item getItem(Long itemId) {
        return itemDao.getOne(itemId);
    }

    public List<Item> getAllItemsCreatedByUser(User user) {
        return itemDao.findAllByOwner(user);
    }

    public void deleteAllItemsCreatedByUser(User user) {
        List<Item> items = getAllItemsCreatedByUser(user);
        itemDao.delete(items);
    }

    public void deleteItem(Long itemId) {
        itemDao.delete(itemId);
    }

}
