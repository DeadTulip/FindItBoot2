package p.hh.fiboot2.init;


import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import p.hh.fiboot2.domain.DigitalItem;
import p.hh.fiboot2.domain.Item;
import p.hh.fiboot2.domain.PhysicalItem;
import p.hh.fiboot2.domain.User;
import p.hh.fiboot2.dto.DigitalItemDto;
import p.hh.fiboot2.dto.ItemDto;
import p.hh.fiboot2.dto.PhysicalItemDto;
import p.hh.fiboot2.dto.UserDto;

public class ModelMapperCreator {

    private ModelMapper modelMapper = new ModelMapper();

    public ModelMapper create() {
        addDigitalItem2DtoMapping();
        addPhysicalItem2DtoMapping();
        return modelMapper;
    }

    private void addDigitalItem2DtoMapping() {
        modelMapper.addMappings(
            new PropertyMap<DigitalItem, DigitalItemDto>() {
                @Override
                protected void configure() {
                    map().setItemType("Digital");
                }
            }
        );
    }

    private void addPhysicalItem2DtoMapping() {
        modelMapper.addMappings(
            new PropertyMap<PhysicalItem, PhysicalItemDto>() {
                @Override
                protected void configure() {
                    map().setItemType("Physical");
                }
            }
        );
    }

    private void addUser2DtoMapping() {
        modelMapper.addMappings(
            new PropertyMap<User, UserDto>() {
                @Override
                protected void configure() {
                    map().setPassword(null);
                }
            }
        );
    }

}
