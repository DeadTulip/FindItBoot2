package p.hh.fiboot2.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TeamDaoTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private TeamDao teamDao;


    @Test
    public void basicCrudTest() {
        // create user
        User user = new User();
        user.setUsername("myUsername");
        user.setPassword("myPassword");
        userDao.save(user);

        // create team
        String teamName = "myTeamName";
        assertNull(teamDao.findByTeamName(teamName));
        Team team = new Team();
        team.setCreator(user);
        team.setTeamName(teamName);
        teamDao.save(team);

        // read
        Team createdTeam = teamDao.findByTeamName(teamName);
        assertNotNull(createdTeam);
        assertNotNull(team.getId());
        assertEquals(teamName, createdTeam.getTeamName());
        assertEquals("myUsername", createdTeam.getCreator().getUsername());

        // update
        String newTeamName = "myNewTeamName";
        assertNull(teamDao.findByTeamName(newTeamName));
        createdTeam.setTeamName(newTeamName);
        teamDao.save(createdTeam);
        assertNull(teamDao.findByTeamName(teamName));
        assertNotNull(teamDao.findByTeamName(newTeamName));

        // delete
        teamDao.delete(createdTeam);
        assertNull(teamDao.findByTeamName(newTeamName));

        // creator is not deleted
        assertNotNull(userDao.findByUsername("myUsername"));
    }
}
