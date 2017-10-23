package p.hh.fiboot2.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDetailDto {
    private Long userId;
    private String username;
    private List<TeamDto> createdTeams;
    private List<TeamDto> joinedTeams;
    private List<ItemDto> createdItems;
    private List<ItemDto> joinedItems;
}
