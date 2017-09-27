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
        assertEquals(teamDao.findAllByTeamName(teamName).size(), 0);
        Team team = new Team();
        team.setCreator(user);
        team.setTeamName(teamName);
        teamDao.save(team);

        // read
        List<Team> teamList = teamDao.findAllByTeamName(teamName);
        assertEquals(teamList.size(), 1);
        Team createdTeam = teamList.get(0);
        assertNotNull(team.getId());
        assertEquals(teamName, createdTeam.getTeamName());
        assertEquals("myUsername", createdTeam.getCreator().getUsername());

        // update
        String newTeamName = "myNewTeamName";
        assertEquals(teamDao.findAllByTeamName(newTeamName).size(), 0);
        createdTeam.setTeamName(newTeamName);
        teamDao.save(createdTeam);
        assertEquals(teamDao.findAllByTeamName(teamName).size(), 0);
        assertEquals(teamDao.findAllByTeamName(newTeamName).size(), 1);

        // delete
        teamDao.delete(createdTeam);
        assertEquals(teamDao.findAllByTeamName(newTeamName).size(), 0);

        // creator is not deleted
        assertEquals(userDao.findAllByUsername("myUsername").size(), 1);
    }
}
