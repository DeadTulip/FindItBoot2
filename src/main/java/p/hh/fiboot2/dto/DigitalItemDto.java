package p.hh.fiboot2.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;

@Data
@ToString(callSuper = true)
public class DigitalItemDto extends ItemDto {

    @Setter(AccessLevel.NONE)
    private String itemType = "Digital";
    private String originalFileName;
    private String fileName;
    private String fileType;
    private Long fileSize;

}
