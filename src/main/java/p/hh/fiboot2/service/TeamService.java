package p.hh.fiboot2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import p.hh.fiboot2.dao.TeamDao;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;

import java.util.List;

@Service
public class TeamService {

    @Autowired
    private TeamDao teamDao;

    public Team getTeam(Long teamId) {
        return teamDao.getOne(teamId);
    }

    public List<Team> getAllTeamsCreatedByUser(User user) {
        return teamDao.findAllByCreator(user);
    }

    public void deleteAllTeamsCreatedByUser(User user) {
        List<Team> teams = teamDao.findAllByCreator(user);
        teamDao.delete(teams);
    }

    public void deleteTeam(Long teamId) {
        teamDao.delete(teamId);
    }

}
