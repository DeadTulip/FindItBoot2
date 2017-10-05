package p.hh.fiboot2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;

import java.util.List;

/**
 * Created by Atos on 21-9-2017.
 */
public interface TeamDao extends JpaRepository<Team, Long> {
    Team findByTeamName(String teamName);
    List<Team> findAllByCreator(User user);
}
