package school.hei.federationagricoleapi.controller.dto;

import lombok.Data;
import school.hei.federationagricoleapi.entity.MemberOccupation;

import java.time.LocalDate;
import java.util.List;

@Data
public class CreateCollectivityActivityRequest {

    private String id;

    private String label;
    private String activityType;

    private List<MemberOccupation> memberOccupationConcerned;

    private LocalDate executiveDate;

    private Integer weekOrdinal;
    private String dayOfWeek;
}