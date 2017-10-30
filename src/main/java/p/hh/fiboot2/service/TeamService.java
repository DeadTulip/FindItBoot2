package p.hh.fiboot2.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot2.dao.TeamDao;
import p.hh.fiboot2.dao.UserDao;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;
import p.hh.fiboot2.dto.MappingUtil;
import p.hh.fiboot2.dto.TeamDto;
import p.hh.fiboot2.exception.DuplicateResourceException;
import p.hh.fiboot2.exception.ResourceNotFoundException;

import java.util.List;
import java.util.function.Predicate;

@Service
public class TeamService {

    @Autowired
    private TeamDao teamDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    public Team getTeam(Long teamId) {
        return teamDao.findOne(teamId);
    }

    public List<TeamDto> getAllTeamsCreatedByUser(Long userId) {
        User user = userDao.findOne(userId);
        return MappingUtil.mapTeamList(modelMapper, teamDao.findAllByCreator(user));
    }

    public TeamDto createTeam(TeamDto teamDto) throws DuplicateResourceException {
        Team teamWithName = teamDao.findByTeamName(teamDto.getTeamName());
        if (teamWithName != null) {
            throw new DuplicateResourceException("Team", teamDto.getTeamName());
        }
        Team team = modelMapper.map(teamDto, Team.class);
        User creator = userDao.findOne(teamDto.getCreator().getUserId());
        team.setCreator(creator);
        Team savedTeam = teamDao.save(team);
        teamDto.setTeamId(savedTeam.getId());
        return teamDto;
    }

    public void deleteAllTeamsCreatedByUser(Long userId) {
        User user = userDao.findOne(userId);
        List<Team> teams = teamDao.findAllByCreator(user);
        teamDao.delete(teams);
    }

    public void deleteTeam(Long teamId) {
        teamDao.delete(teamId);
    }

    public void addMember(Long teamId, String userName) throws ResourceNotFoundException {
        Team team = teamDao.findOne(teamId);
        User user = userDao.findByUsername(userName);
        if (user != null && team.getCreator().getId() != user.getId()) {
            boolean contains = team.getMembers().stream().anyMatch(u -> u.getId().equals(user.getId()));
            if(!contains) {
                team.getMembers().add(user);
                teamDao.save(team);
            }
        } else {
            throw new ResourceNotFoundException("User", userName);
        }
    }

    public void removeMember(Long teamId, Long userId) {
        Team team = teamDao.findOne(teamId);
        User user = userDao.findOne(userId);
        team.getMembers().remove(user);
        teamDao.save(team);
    }
}
