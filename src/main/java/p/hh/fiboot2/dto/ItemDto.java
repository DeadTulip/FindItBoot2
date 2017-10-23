package p.hh.fiboot2.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ItemDto {

    private Long itemId;
    private UserDto owner;
    private String name;
    private String eventStartTime;
    private String eventEndTime;
    private String involvedPeople;
    private String involvedPlaces;
    private String description;
    private String itemType;
}
