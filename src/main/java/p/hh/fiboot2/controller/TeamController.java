package p.hh.fiboot2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.dto.TeamDto;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    public TeamDto createTeam() {
        return null;
    }

    @GetMapping("/{teamId}")
    public TeamDto readTeam(@PathVariable Long teamId) {
        return null;
    }

    @PostMapping("/udpate")
    public TeamDto updateTeam() {
        return null;
    }

    @DeleteMapping("/{teamId}")
    public TeamDto deleteTeam() {
        return null;
    }
}
