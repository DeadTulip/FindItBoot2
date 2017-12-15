package p.hh.fiboot2.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class DigitalItemDto extends ItemDto {

    private String originalFileName;
    private String fileType;
    private Long fileSize;
    private String fileContent;
}
