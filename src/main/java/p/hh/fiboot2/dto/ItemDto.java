package p.hh.fiboot2.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class ItemDto {

    private Long itemId;
    private UserDto owner;
    private String name;
    private String dateCreated;
    private String dateUpdated;
    private String eventStartTime;
    private String eventEndTime;
    private String involvedPeople;
    private String involvedPlaces;
    private String description;
    private String itemType;
    private List<String> sharedTeams = new ArrayList<>();

}
