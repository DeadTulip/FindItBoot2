package p.hh.fiboot2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import p.hh.fiboot2.domain.User;

import java.util.List;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
