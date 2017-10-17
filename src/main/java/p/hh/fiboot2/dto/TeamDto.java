package p.hh.fiboot2.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamDto {

    private Long teamId;
    private String teamName;
    private UserDto creator;
    private List<UserDto> members;
}
