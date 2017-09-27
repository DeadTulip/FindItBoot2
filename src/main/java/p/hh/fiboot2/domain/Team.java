package p.hh.fiboot2.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table
@Data
public class Team extends BaseEntity {

    @Column
    private String teamName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator")
    private User creator;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "team_user",
            joinColumns = { @JoinColumn(name = "team_id", nullable = false) },
            inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false) }
    )
    private final Set<User> members = new HashSet<User>();

}
