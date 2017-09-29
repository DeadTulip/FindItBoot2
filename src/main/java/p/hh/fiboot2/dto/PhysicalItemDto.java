package p.hh.fiboot2.dto;


import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString(callSuper = true)
public class PhysicalItemDto extends ItemDto {

    @Setter(AccessLevel.NONE)
    private String itemType = "Physical";
    private Float length;
    private Float width;
    private Float height;
}