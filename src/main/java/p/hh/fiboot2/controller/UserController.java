package p.hh.fiboot2.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;
import p.hh.fiboot2.dto.MappingUtil;
import p.hh.fiboot2.dto.UserDto;
import p.hh.fiboot2.service.ItemService;
import p.hh.fiboot2.service.TeamService;
import p.hh.fiboot2.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired private ModelMapper modelMapper;
    @Autowired private UserService userService;
    @Autowired private TeamService teamService;
    @Autowired private ItemService itemService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User createdUser = userService.createUser(user);
        UserDto createdUserDto = modelMapper.map(user, UserDto.class);
        return createdUserDto;
    }

    @GetMapping("/{userId}")
    public UserDto readUser(@PathVariable Long userId, HttpServletResponse response) {
        User user = userService.getUser(userId);
        if (user != null) {
            return modelMapper.map(user, UserDto.class);
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PutMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User updatedUser = userService.updateUser(userId, user);
        return modelMapper.map(updatedUser, UserDto.class);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userService.getUser(userId));
    }

    @GetMapping("/{userId}/full")
    public UserDto getUserFullInfo(@PathVariable Long userId) {
        User user = userService.getUser(userId);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setJoinedTeams(MappingUtil.mapTeamList(modelMapper, new ArrayList<>(user.getJoinedTeams())));

        List<Team> teamList = teamService.getAllTeamsCreatedByUser(user);
        userDto.setCreatedTeams(MappingUtil.mapTeamList(modelMapper, teamList));

        List<Item> itemList = itemService.getAllItemsCreatedByUser(user);
        userDto.setCreatedItems(MappingUtil.mapItemList(modelMapper, itemList));

        List<Item> accessibleItemList = userService.getAllAccessibleItems(user);
        userDto.setAccessibleItems(MappingUtil.mapItemList(modelMapper, accessibleItemList));

        return userDto;
    }
}
