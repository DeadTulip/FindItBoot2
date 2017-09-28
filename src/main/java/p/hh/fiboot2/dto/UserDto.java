package p.hh.fiboot2.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long userId;
    private String username;
    private String role;
    private TeamDto[] createdTeams;
    private TeamDto[] joinedTeams;
    private ItemDto[] createdItems;
    private ItemDto[] accessibleItems;
}
