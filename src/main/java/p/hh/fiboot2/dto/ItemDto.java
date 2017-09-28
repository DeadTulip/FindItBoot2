package p.hh.fiboot2.dto;

import lombok.Data;

import java.util.Date;

@Data
public abstract class ItemDto {

    private Long itemId;
    private UserDto owner;
    private String name;
    private Date dateCreated;
    private Date dateUpdated;
    private Date eventStartTime;
    private Date eventEndTime;
    private String involvedPeople;
    private String involvedPlaces;
    private String description;
}
