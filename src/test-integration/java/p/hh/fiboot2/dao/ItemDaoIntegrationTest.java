package p.hh.fiboot2.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import p.hh.fiboot2.domain.DigitalItem;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.PhysicalItem;
import p.hh.fiboot2.domain.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ItemDaoIntegrationTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ItemDao itemDao;

    @Test
    public void basicCurdTest() {
        // create user
        User user = new User();
        user.setUsername("myUsername");
        user.setPassword("myPassword");
        userDao.save(user);

        // create digital item
        DigitalItem di = new DigitalItem();
        di.setName("myDigitalItem");
        di.setOwner(user);
        di.setDateCreated(new Date());
        di.setDateUpdated(new Date());
        di.setOriginalFileName("myOriginalFileName");
        di.setFileName("myFileName");
        itemDao.save(di);

        // create physical item
        PhysicalItem pi = new PhysicalItem();
        pi.setName("myPhysicalItem");
        pi.setOwner(user);
        pi.setDateCreated(new Date());
        pi.setDateUpdated(new Date());
        pi.setHeight(1.0f);
        itemDao.save(pi);

        List<Item> itemList = itemDao.findAllByOwner(user);
        assertEquals(itemList.size(), 2);

    }
}
