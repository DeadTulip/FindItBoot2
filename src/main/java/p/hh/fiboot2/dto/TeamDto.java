package p.hh.fiboot2.dto;

import lombok.Data;

@Data
public class TeamDto {

    private Long teamId;
    private String teamName;
    private UserDto creator;
    private UserDto[] members;
}
