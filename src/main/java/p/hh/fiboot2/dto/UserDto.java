package p.hh.fiboot2.dto;

import lombok.Data;

@Data
public class UserDto extends BaseDto {
    private Long userId;
    private String username;
    private String password;
    private String roles;
}
