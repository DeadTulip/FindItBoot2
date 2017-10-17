package p.hh.fiboot2.dto;


import org.modelmapper.ModelMapper;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.PhysicalItem;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappingUtil {

    public static List<UserDto> mapUserList(ModelMapper modelMapper, List<User> userList) {
        return userList.stream().map(it -> modelMapper.map(it, UserDto.class)).collect(Collectors.toList());
    }

    public static List<TeamDto> mapTeamList(ModelMapper modelMapper, List<Team> teamList) {
        return teamList.stream().map(it -> modelMapper.map(it, TeamDto.class)).collect(Collectors.toList());
    }

    public static List<ItemDto> mapItemList(ModelMapper modelMapper, List<Item> itemList) {
        return itemList.stream().map(
                it -> {
                    if (it instanceof PhysicalItem) {
                        return modelMapper.map(it, PhysicalItemDto.class);
                    } else {
                        return modelMapper.map(it, DigitalItemDto.class);
                    }
                }
            ).collect(Collectors.toList());
    }
}
