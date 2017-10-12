package p.hh.fiboot2.controller;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.dto.UserDto;
import p.hh.fiboot2.service.UserService;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    @Getter @Setter
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @GetMapping("/{userId}")
    public UserDto readUser(@PathVariable Long userId, HttpServletResponse response) {
        UserDto userDto = userService.getUser(userId);
        if (userDto != null) {
            return userDto;
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null;
        }
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

//    @GetMapping("/{userId}/full")
//    public UserDto getUserFullInfo(@PathVariable Long userId) {
//        User user = userService.getUser(userId);
//        UserDto userDto = modelMapper.map(user, UserDto.class);
//        userDto.setJoinedTeams(MappingUtil.mapTeamList(modelMapper, new ArrayList<>(user.getJoinedTeams())));
//
//        List<Team> teamList = teamService.getAllTeamsCreatedByUser(user);
//        userDto.setCreatedTeams(MappingUtil.mapTeamList(modelMapper, teamList));
//
//        List<Item> itemList = itemService.getAllItemsCreatedByUser(user);
//        userDto.setCreatedItems(MappingUtil.mapItemList(modelMapper, itemList));
//
//        List<Item> accessibleItemList = userService.getAllAccessibleItems(user);
//        userDto.setAccessibleItems(MappingUtil.mapItemList(modelMapper, accessibleItemList));
//
//        return userDto;
//    }
}
