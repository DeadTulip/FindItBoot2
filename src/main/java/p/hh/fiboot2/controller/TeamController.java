package p.hh.fiboot2.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.dto.TeamDto;
import p.hh.fiboot2.service.TeamService;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TeamService teamService;

    @PostMapping
    public TeamDto createTeam() {
        return null;
    }

    @GetMapping("/{teamId}")
    public TeamDto readTeam(@PathVariable Long teamId) {
        Team team = teamService.getTeam(teamId);
        return modelMapper.map(team, TeamDto.class);
    }

    @PostMapping("/udpate")
    public TeamDto updateTeam() {
        return null;
    }

    @DeleteMapping("/{teamId}")
    public void deleteTeam(@PathVariable Long teamId) {
        teamService.deleteTeam(teamId);
    }
}
