package p.hh.fiboot2.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.User;

import java.util.List;

public interface ItemDao extends JpaRepository<Item, Long> {
    List<Item> findAllByOwner(User user);
}
