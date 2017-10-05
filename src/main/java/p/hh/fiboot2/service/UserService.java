package p.hh.fiboot2.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot2.dao.UserDao;
import p.hh.fiboot2.domain.User;
import p.hh.fiboot2.dto.UserDto;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ItemService itemService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto getUser(Long userId) {
        User user = userDao.findOne(userId);
        return user != null ? modelMapper.map(user, UserDto.class) : null;
    }

    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userDao.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUser(UserDto userDto) {
        User userToBeUpdated = userDao.getOne(userDto.getUserId());
        userToBeUpdated.setUsername(userDto.getUsername());
        User updatedUser = userDao.save(userToBeUpdated);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    public void deleteUser(Long userId) {
        User user = userDao.getOne(userId);
        if (user != null) {
            itemService.deleteAllItemsCreatedByUser(userId);
            teamService.deleteAllTeamsCreatedByUser(userId);
            userDao.delete(user);
        }
    }

//    public List<Item> getAllAccessibleItems(User user) {
//        List<Team> createdTeamList = teamService.getAllTeamsCreatedByUser(user);
//        List<Team> joinedTeamList = new ArrayList<>(user.getJoinedTeams());
//        List<Item> accessibleItems =
//                Stream.concat(createdTeamList.stream(), joinedTeamList.stream())
//                    .flatMap(t -> t.getMembers().stream())
//                    .distinct()
//                    .flatMap(u -> itemService.getAllItemsCreatedByUser(u).stream())
//                    .distinct()
//                    .collect(Collectors.toList());
//        return accessibleItems;
//    }

}
