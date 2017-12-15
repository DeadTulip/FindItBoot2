package p.hh.fiboot2.domain;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "id")
@Data
@ToString(callSuper = true)
public class DigitalItem extends Item {

    @Column
    private String originalFileName;

    @Column
    private String fileType;

    @Column
    private Long fileSize;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "MEDIUMTEXT")
    private String fileContent;
}
