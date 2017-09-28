package p.hh.fiboot2.controller;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.dto.UserDto;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public UserDto createUser() {
        return null;
    }

    @GetMapping("/{userId}")
    public UserDto readUser(@PathVariable Long userId) {
        return null;
    }

    @DeleteMapping("/{userId}")
    public UserDto deleteUser(@PathVariable Long userId) {
        return null;
    }

    @GetMapping("/{userId}/full")
    public UserDto getUserFullInfo(@PathVariable Long userId) {
        return null;
    }
}
