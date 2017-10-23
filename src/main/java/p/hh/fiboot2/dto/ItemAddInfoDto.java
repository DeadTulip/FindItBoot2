package p.hh.fiboot2.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ItemAddInfoDto {
    @JsonProperty
    private List<String> sharedTeamsOptions;
    @JsonProperty
    private List<String> involvedPeopleOptions;
    @JsonProperty
    private List<String> involvedPlaceOptions;
}
