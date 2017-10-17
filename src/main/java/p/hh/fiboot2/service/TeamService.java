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

import java.util.List;

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

    public TeamDto createTeam(TeamDto teamDto) {
        Team team = modelMapper.map(teamDto, Team.class);
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

    public void addMember(Long teamId, Long userId) {
        Team team = teamDao.findOne(teamId);
        User user = userDao.findOne(userId);
        team.getMembers().add(user);
        teamDao.save(team);
    }

    public void removeMember(Long teamId, Long userId) {
        Team team = teamDao.findOne(teamId);
        User user = userDao.findOne(userId);
        team.getMembers().remove(user);
        teamDao.save(team);
    }
}
