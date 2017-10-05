package p.hh.fiboot2.dao;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import p.hh.fiboot2.domain.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeamDao teamDao;

    @Test
    public void basicCrudTest() {
        String userName = "myUsername";
        String password = "myPassword";
        assertNull(userDao.findByUsername(userName));

        // create
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        userDao.save(user);

        // read
        User createdUser = userDao.findByUsername(userName);
        assertNotNull(createdUser);
        assertNotNull(user.getId());
        assertEquals(userName, createdUser.getUsername());
        assertEquals(password, createdUser.getPassword());

        // update
        String newUserName = "myNewUsername";
        assertNull(userDao.findByUsername(newUserName));
        createdUser.setUsername(newUserName);
        userDao.save(createdUser);
        assertNull(userDao.findByUsername(userName));
        assertNotNull(userDao.findByUsername(newUserName));

        // delete
        userDao.delete(createdUser);
        assertNull(userDao.findByUsername(newUserName));
    }


}
