package p.hh.fiboot2.dto;


import org.modelmapper.ModelMapper;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.PhysicalItem;
import p.hh.fiboot2.domain.Team;

import java.util.List;

public class MappingUtil {
    public static TeamDto[] mapTeamList(ModelMapper modelMapper, List<Team> teamList) {
        return teamList.stream().map(it -> modelMapper.map(it, TeamDto.class)).toArray(TeamDto[]::new);
    }

    public static ItemDto[] mapItemList(ModelMapper modelMapper, List<Item> itemList) {
        return itemList.stream().map(
                it -> {
                    if (it instanceof PhysicalItem) {
                        return modelMapper.map(it, PhysicalItemDto.class);
                    } else {
                        return modelMapper.map(it, DigitalItemDto.class);
                    }
                }
            ).toArray(ItemDto[]::new);
    }
}
