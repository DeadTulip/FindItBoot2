package p.hh.fiboot2.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ItemAddInfoDto {
    private List<String> sharedTeamsOptions = new ArrayList<>();
    private List<String> involvedPeopleOptions = new ArrayList<>();
    private List<String> involvedPlaceOptions = new ArrayList<>();
}
