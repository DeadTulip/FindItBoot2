package p.hh.fiboot2.dto;

import p.hh.fiboot2.domain.*;

import static org.junit.Assert.assertEquals;

public class MappingUtil {

    static void verifyUserMapping(User user, UserDto userDto) {
        assertEquals(user.getId(), userDto.getUserId());
        assertEquals(user.getUsername(), userDto.getUsername());
    }

    static void verifyTeamMapping(Team team, TeamDto teamDto) {
        assertEquals(team.getId(), teamDto.getTeamId());
        assertEquals(team.getTeamName(), teamDto.getTeamName());
        verifyUserMapping(team.getCreator(), teamDto.getCreator());
    }

    static void verifyItemMapping(Item item, ItemDto itemDto) {
        assertEquals(item.getId(), itemDto.getItemId());
        verifyUserMapping(item.getOwner(), itemDto.getOwner());
        assertEquals(item.getName(), itemDto.getName());
        assertEquals(item.getDateCreated(), itemDto.getDateCreated());
        assertEquals(item.getDateUpdated(), itemDto.getDateUpdated());
        assertEquals(item.getEventStartTime(), itemDto.getEventStartTime());
        assertEquals(item.getEventEndTime(), itemDto.getEventEndTime());
        assertEquals(item.getInvolvedPeople(), itemDto.getInvolvedPeople());
        assertEquals(item.getInvolvedPlaces(), itemDto.getInvolvedPlaces());
        assertEquals(item.getDescription(), itemDto.getDescription());
    }

    static void verifyDigitalItemMapping(DigitalItem di, DigitalItemDto diDto) {
        assertEquals("Digital", diDto.getItemType());
        verifyItemMapping(di, diDto);
        assertEquals(di.getOriginalFileName(), diDto.getOriginalFileName());
        assertEquals(di.getFileName(), diDto.getFileName());
        assertEquals(di.getFileSize(), diDto.getFileSize());
        assertEquals(di.getFileType(), diDto.getFileType());
    }

    static void verifyPhysicalItemMapping(PhysicalItem pi, PhysicalItemDto piDto) {
        assertEquals("Physical", piDto.getItemType());
        verifyItemMapping(pi, piDto);
        assertEquals(pi.getLength(), piDto.getLength());
        assertEquals(pi.getWidth(), piDto.getWidth());
        assertEquals(pi.getHeight(), piDto.getHeight());
    }

}
