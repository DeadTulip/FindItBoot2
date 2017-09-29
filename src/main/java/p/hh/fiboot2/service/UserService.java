package p.hh.fiboot2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot2.dao.UserDao;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TeamService teamService;

    public User getUser(Long userId) {
        return userDao.findOne(userId);
    }

    public User createUser(User user) {
        return userDao.save(user);
    }

    public void deleteUser(User user) {
        itemService.deleteAllItemsCreatedByUser(user);
        teamService.deleteAllTeamsCreatedByUser(user);
        userDao.delete(user);
    }

    public List<Item> getAllAccessibleItems(User user) {
        List<Team> createdTeamList = teamService.getAllTeamsCreatedByUser(user);
        List<Team> joinedTeamList = new ArrayList<>(user.getJoinedTeams());
        List<Item> accessibleItems =
                Stream.concat(createdTeamList.stream(), joinedTeamList.stream())
                    .flatMap(t -> t.getMembers().stream())
                    .distinct()
                    .flatMap(u -> itemService.getAllItemsCreatedByUser(u).stream())
                    .distinct()
                    .collect(Collectors.toList());
        return accessibleItems;
    }

}
