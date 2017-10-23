package p.hh.fiboot2.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot2.dao.UserDao;
import p.hh.fiboot2.domain.User;
import p.hh.fiboot2.dto.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

    @Autowired
    private ModelMapper modelMapper;

    public UserDto getUser(Long userId) {
        User user = userDao.findOne(userId);
        return user != null ? modelMapper.map(user, UserDto.class) : null;
    }

    public List<UserDto> getAllUsers() {
        List<User> userList = userDao.findAll();
        return MappingUtil.mapUserList(modelMapper, userList);
    }

    public UserDto createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        userDao.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUser(UserDto userDto) {
        User userToBeUpdated = userDao.findOne(userDto.getUserId());
        userToBeUpdated.setUsername(userDto.getUsername());
        User updatedUser = userDao.save(userToBeUpdated);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    public void deleteUser(Long userId) {
        User user = userDao.findOne(userId);
        if (user != null) {
            itemService.deleteAllItemsCreatedByUser(userId);
            teamService.deleteAllTeamsCreatedByUser(userId);
            userDao.delete(user);
        }
    }

    public UserDetailDto getUserDetail(Long userId) {
        UserDetailDto userDetailDto = null;
        User user = userDao.findOne(userId);
        if(user != null) {
            userDetailDto = new UserDetailDto();
            userDetailDto.setUserId(user.getId());
            userDetailDto.setUsername(user.getUsername());

            List<TeamDto> createdTeamList = teamService.getAllTeamsCreatedByUser(userId);
            userDetailDto.setCreatedTeams(createdTeamList);

            List<TeamDto> joinedTeamList = MappingUtil.mapTeamList(modelMapper, new ArrayList<>(user.getJoinedTeams()));
            userDetailDto.setJoinedTeams(joinedTeamList);

            List<ItemDto> createdItemList = itemService.getAllItemsCreatedByUser(userId);
            userDetailDto.setCreatedItems(createdItemList);

            List<ItemDto> joinedItemList =
                    Stream.concat(createdTeamList.stream(), joinedTeamList.stream())
                            .flatMap(t -> t.getMembers().stream())
                            .distinct()
                            .flatMap(u -> itemService.getAllItemsCreatedByUser(u.getUserId()).stream())
                            .collect(Collectors.toList());
            userDetailDto.setJoinedItems(joinedItemList);
        }
        return userDetailDto;
    }

    public ItemAddInfoDto toAddItem(Long userId) {
        ItemAddInfoDto dto = new ItemAddInfoDto();
        List<TeamDto> createdTeamList = teamService.getAllTeamsCreatedByUser(userId);
        dto.setSharedTeamsOptions(
                createdTeamList.stream().map(TeamDto::getTeamName).collect(Collectors.toList()));

        List<ItemDto> createdItemList = itemService.getAllItemsCreatedByUser(userId);
        List<String> involvedPeopleOptions = createdItemList.stream()
                .map(ItemDto::getInvolvedPeople)
                .flatMap(it -> Arrays.stream(it.split(",")))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        dto.setInvolvedPeopleOptions(involvedPeopleOptions);

        List<String> involvedPlaceOptions = createdItemList.stream()
                .map(ItemDto::getInvolvedPlaces)
                .flatMap(it -> Arrays.stream(it.split(",")))
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        dto.setInvolvedPlaceOptions(involvedPlaceOptions);
        return dto;
    }
}
