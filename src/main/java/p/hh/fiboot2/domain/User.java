package p.hh.fiboot2.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class User extends BaseEntity {

    private static final long serialVersionUID = -3512521164157856626L;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Column
    String username;

    @Column
    String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_user",
            joinColumns = { @JoinColumn(name = "user_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "team_id", nullable = false) }
    )
    @Setter(AccessLevel.NONE)
    private final Set<Team> joinedTeams = new HashSet<Team>();


}
