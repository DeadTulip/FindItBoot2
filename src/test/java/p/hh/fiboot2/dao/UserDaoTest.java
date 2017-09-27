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
        assertEquals(userDao.findAllByUsername(userName).size(), 0);

        // create
        User user = new User();
        user.setUsername(userName);
        user.setPassword(password);
        userDao.save(user);

        // read
        List<User> userList = userDao.findAllByUsername(userName);
        assertEquals(userList.size(), 1);
        User createdUser = userList.get(0);
        assertNotNull(user.getId());
        assertEquals(userName, createdUser.getUsername());
        assertEquals(password, createdUser.getPassword());

        // update
        String newUserName = "myNewUsername";
        assertEquals(userDao.findAllByUsername(newUserName).size(), 0);
        createdUser.setUsername(newUserName);
        userDao.save(createdUser);
        assertEquals(userDao.findAllByUsername(userName).size(), 0);
        assertEquals(userDao.findAllByUsername(newUserName).size(), 1);

        // delete
        userDao.delete(createdUser);
        assertEquals(userDao.findAllByUsername(newUserName).size(), 0);
    }


}
