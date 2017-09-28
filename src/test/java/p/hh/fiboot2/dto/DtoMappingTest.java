package p.hh.fiboot2.dto;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import p.hh.fiboot2.domain.DigitalItem;
import p.hh.fiboot2.domain.PhysicalItem;
import p.hh.fiboot2.domain.Team;
import p.hh.fiboot2.domain.User;


public class DtoMappingTest {

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void testUserDtoMapping() {
        User user = DomainUtil.createUser();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        System.out.println(userDto);
        MappingUtil.verifyUserMapping(user, userDto);
    }

    @Test
    public void testTeamDtoMapping() {
        User user = DomainUtil.createUser();
        Team team = DomainUtil.createTeam(user);

        TeamDto teamDto = modelMapper.map(team, TeamDto.class);
        System.out.println(teamDto);
        MappingUtil.verifyTeamMapping(team, teamDto);
    }

    @Test
    public void testDigitalItemDtoMapping() {
        User user = DomainUtil.createUser();
        DigitalItem di = DomainUtil.createDigitalItem(user);
        DigitalItemDto diDto = modelMapper.map(di, DigitalItemDto.class);
        System.out.println(diDto);
        MappingUtil.verifyDigitalItemMapping(di, diDto);
    }

    @Test
    public void testPhysicalItemDtoMapping() {
        User user = DomainUtil.createUser();
        PhysicalItem pi = DomainUtil.createPhysicalItem(user);
        PhysicalItemDto piDto = modelMapper.map(pi, PhysicalItemDto.class);
        System.out.println(piDto);
        MappingUtil.verifyPhysicalItemMapping(pi, piDto);
    }

    @Test
    public void testUserDtoMappingWithCreatedItems() {
        User user = DomainUtil.createUser();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        System.out.println(userDto);
        MappingUtil.verifyUserMapping(user, userDto);
    }

}
