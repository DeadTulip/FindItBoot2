package p.hh.fiboot2.dto;


import p.hh.fiboot2.domain.*;

import java.lang.reflect.Field;
import java.util.Date;

public class DomainUtil {

    static User createUser() {
        User user = new User();
        setEntityId(user, 1L);
        user.setUsername("myUserName");
        return user;
    }

    static Team createTeam(User creator) {
        Team team = new Team();
        setEntityId(team, 2L);
        team.setCreator(creator);
        team.setTeamName("myTeamName");
        return team;
    }

    static void fillItem(Item item, User user) {
        setEntityId(item, 3L);
        item.setOwner(user);
        item.setName("myItemName");
        item.setDateCreated(new Date());
        item.setDateUpdated(new Date());
        item.setEventStartTime(new Date());
        item.setEventEndTime(new Date());
        item.setInvolvedPeople("1,2,3");
        item.setInvolvedPlaces("4,5,6");
        item.setDescription("myItemDescription");
    }

    static DigitalItem createDigitalItem(User user) {
        DigitalItem di = new DigitalItem();
        fillItem(di, user);
        di.setOriginalFileName("myOriginalFileName");
        di.setFileName("myFileName");
        di.setFileType("myFileType");
        di.setFileSize(1234L);
        return di;
    }

    static PhysicalItem createPhysicalItem(User user) {
        PhysicalItem pi = new PhysicalItem();
        fillItem(pi, user);
        pi.setHeight(1.0f);
        pi.setLength(2.0f);
        pi.setWidth(3.0f);
        return pi;
    }

    static void setEntityId(BaseEntity be, Long id) {
        try {
            Class clazz = be.getClass();
            while(clazz != BaseEntity.class) {
                clazz = clazz.getSuperclass();
            }
            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(be, id);
        } catch (NoSuchFieldException nfe) {

        } catch (IllegalAccessException iae) {

        }
    }
}
