package p.hh.fiboot2.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "item")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
public abstract class Item extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column
    private String name;

    @Column
    private Date dateCreated;

    @Column
    private Date dateUpdated;

    @Column
    private Date eventStartTime;

    @Column
    private Date eventEndTime;

    @Column(name = "people")
    private String involvedPeople;

    @Column(name = "places")
    private String involvedPlaces;

    @Column
    private String description;

    @ManyToMany(mappedBy = "items")
    private final Set<Team> teams = new HashSet<>();
}
